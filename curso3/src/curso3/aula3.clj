(ns curso3.aula3
  (:use [clojure.pprint])
  (:require [curso3.logic :as h.logic]
            [curso3.model :as h.model]))

; def -> símbolo global dentro do namespace
(def nome "guilherme")
; o problema é que o símbolo pode ser redefinido com o root binding
(def nome 345576)

(let [nome "guilherme"]
  (+ 1 2)
  (+ 3 4)
  (pprint nome)
  ; nao redefini o símbolo local
  ; cria um novo símbolo local e ESCONDE o anterior
  ; SHADOWING
  (let [nome "daniela"]
    (+ 5 6)
    (+ 7 8)
    (pprint nome))
  (pprint nome))

(defn testa-atom []
  (let [hospital-atom (atom {:espera h.model/fila-vazia})]
    (println hospital-atom)
    (pprint hospital-atom)

    (println (deref hospital-atom))
    (pprint (deref hospital-atom))

    ; @ é um atalho para o deref
    (println @hospital-atom)
    (pprint @hospital-atom)

    ; não é assim que o conteúdo dentro do átomo é alterado
    (pprint (assoc @hospital-atom :laboratorio1 h.model/fila-vazia))
    (pprint @hospital-atom)

    ; swap é uma das maneiras de alterar o conteúdo dentro de um átomo
    (swap! hospital-atom assoc :laboratorio1 h.model/fila-vazia)
    (pprint @hospital-atom)

    (swap! hospital-atom assoc :laboratorio2 h.model/fila-vazia)
    (pprint @hospital-atom)

    (swap! hospital-atom update :laboratorio1 conj 111)
    (pprint @hospital-atom)))

; (testa-atom)

(defn chega-em-swap! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "apos inserir pessoa" pessoa))

(defn simula-um-dia-em-paralelo []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-swap! hospital 111))))
    (.start (Thread. (fn [] (chega-em-swap! hospital 222))))
    (.start (Thread. (fn [] (chega-em-swap! hospital 333))))
    (.start (Thread. (fn [] (chega-em-swap! hospital 444))))
    (.start (Thread. (fn [] (chega-em-swap! hospital 555))))
    (.start (Thread. (fn [] (chega-em-swap! hospital 666))))
    (Thread/sleep 15000)
    (.start (Thread. (fn [] (pprint hospital))))))

(simula-um-dia-em-paralelo)

; swap implementa uma política de retry que verifica a versão do átomo
