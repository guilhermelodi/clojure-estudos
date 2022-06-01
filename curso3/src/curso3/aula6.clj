(ns curso3.aula6
  (:use [clojure.pprint])
  (:require [curso3.model :as h.model]))

(defn sala-disponivel?
  [sala]
  (-> sala
      count
      (< 5)))

(defn chega-em
  [sala pessoa]
  (if (sala-disponivel? sala)
    (conj sala pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em!
  "Troca de referência via ref-set"
  [hospital pessoa]
  (let [sala (get hospital :espera)]
    (ref-set sala (chega-em @sala pessoa))))                ; @ -> deref

(defn chega-em!
  "Troca de referência via alter"
  [hospital pessoa]
  (let [sala (get hospital :espera)]
    (alter sala chega-em pessoa)))                          ; @ -> deref

(defn simula-um-dia []
  (let [hospital {:espera       (ref h.model/fila-vazia)
                  :laboratorio1 (ref h.model/fila-vazia)
                  :laboratorio2 (ref h.model/fila-vazia)
                  :laboratorio3 (ref h.model/fila-vazia)}]
    (dosync
      (chega-em! hospital "guilherme")
      (chega-em! hospital "joão")
      (chega-em! hospital "pedro")
      (chega-em! hospital "paulo")
      (chega-em! hospital "daniela")
      ;(chega-em! hospital "ana")
      )
    (pprint hospital)))

;(simula-um-dia)

(defn async-chega-em!
  [hospital pessoa]
  (future
    (Thread/sleep (rand 5000))
    (dosync
      ;(pprint "Tentando codigo sincronizado" pessoa)
      (chega-em! hospital pessoa))))

(defn simula-um-dia-async []
  (let [hospital {:espera       (ref h.model/fila-vazia)
                  :laboratorio1 (ref h.model/fila-vazia)
                  :laboratorio2 (ref h.model/fila-vazia)
                  :laboratorio3 (ref h.model/fila-vazia)}]

    ; definindo simbolo global para acessá-lo fora da função quando acabar a execução
    (def futures (mapv #(async-chega-em! hospital %) (range 10)))

    (future
      (dotimes [_ 4]
        (Thread/sleep 2000)
        (pprint hospital)
        (pprint futures)))))

(simula-um-dia-async)

(Thread/sleep 20000)
(pprint futures)