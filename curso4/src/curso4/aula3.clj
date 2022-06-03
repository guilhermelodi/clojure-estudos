(ns curso4.aula3
  (:use clojure.pprint)
  (:require [curso4.logic :as h.logic]))

(defn carrega-paciente [id]
  (println "Carregando" id)
  (Thread/sleep 1000)
  {:id id, :carregado-em (h.logic/agora)})

;(pprint (carrega-paciente 15))
;(pprint (carrega-paciente 20))

;função pura
(defn carrega-se-nao-existe
  [cache id carregadora]
  (if (contains? cache id)
    cache
    (let [objeto (carregadora id)]
      (assoc cache id objeto))))

;(pprint (carrega-se-nao-existe {}, 15, carrega-paciente))
;(pprint (carrega-se-nao-existe { 15 {:id 15} }, 15, carrega-paciente))

(defprotocol Carregavel
  (carrega! [this id]))

(defrecord Cache
  [cache carregadora]

  Carregavel
  (carrega! [this id]
    (swap! cache carrega-se-nao-existe id carregadora)
    (get @cache id)))

(def pacientes (->Cache (atom {}), carrega-paciente))
(pprint pacientes)
(carrega! pacientes 15)
(carrega! pacientes 30)
(carrega! pacientes 15)
(pprint pacientes)