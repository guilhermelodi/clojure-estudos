(ns curso3.aula1
  (:use [clojure pprint])
  (:require [curso3.model :as h.model]
            [curso3.logic :as h.logic]))

(pprint (h.model/novo-hospital))


(defn simula-um-dia []
  (def hospital (h.model/novo-hospital))                    ; root binding
  (def hospital (h.logic/chega-em hospital :espera 111))
  (def hospital (h.logic/chega-em hospital :espera 222))
  (def hospital (h.logic/chega-em hospital :espera 333))
  ; funciona, porém muito ruim por estarmos usando símbolo global com root binding
  (pprint hospital)

  (def hospital (h.logic/chega-em hospital :laboratorio1 444))
  (def hospital (h.logic/chega-em hospital :laboratorio3 555))
  (pprint hospital)

  (def hospital (h.logic/atende hospital :espera))
  (def hospital (h.logic/atende hospital :laboratorio1))
  (pprint hospital)

  (def hospital (h.logic/chega-em hospital :espera 666))
  (def hospital (h.logic/chega-em hospital :espera 777))
  (def hospital (h.logic/chega-em hospital :espera 888))
  (pprint hospital)
  ;(def hospital (h.logic/chega-em hospital :espera 999)) ; lancaria exception pela sala :espera estar cheia
  (pprint hospital))

;(simula-um-dia)

(defn chega-em-definindo [pessoa]
  (def hospital (h.logic/chega-em-pausado hospital :espera pessoa))
  (println "apos inserir pessoa" pessoa))

(defn simula-um-dia-em-paralelo []
  (def hospital (h.model/novo-hospital))                    ; root binding
  ; MUITO CLARO o problema de variavel global (simbolo do namespace) compartilhado entre threads
  (.start (Thread. (fn [] (chega-em-definindo 111))))
  (.start (Thread. (fn [] (chega-em-definindo 222))))
  (.start (Thread. (fn [] (chega-em-definindo 333))))
  (.start (Thread. (fn [] (chega-em-definindo 444))))
  (.start (Thread. (fn [] (chega-em-definindo 555))))
  (.start (Thread. (fn [] (chega-em-definindo 666))))
  (Thread/sleep 20000)
  (.start (Thread. (fn [] (pprint hospital)))))

(simula-um-dia-em-paralelo)