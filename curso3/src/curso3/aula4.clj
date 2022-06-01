(ns curso3.aula4
  (:use [clojure.pprint])
  (:require [curso3.logic :as h.logic]
            [curso3.model :as h.model]))

(defn chega-em-swap! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "apos inserir pessoa" pessoa))

(defn simula-um-dia-em-paralelo-com-mapv []
  "Simulação utilizando mapv para forçar a execução"
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [111, 222, 333, 444, 555, 666]]

    (mapv #(.start (Thread. (fn [] (chega-em-swap! hospital %)))) pessoas)

    (Thread/sleep 15000)
    (.start (Thread. (fn [] (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv)

(defn simula-um-dia-em-paralelo-com-mapv-refatorada []
  "Simulação utilizando mapv para forçar a execução"
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [111, 222, 333, 444, 555, 666]
        starta-thread-de-chegada #(.start (Thread. (fn [] (chega-em-swap! hospital %))))]

    ; como map é lazy, precisamos utilizar o mapv
    (mapv starta-thread-de-chegada pessoas)

    (Thread/sleep 15000)
    (.start (Thread. (fn [] (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-refatorada)

(defn starta-thread-de-chegada
  ([hospital]
   (fn [pessoa] (starta-thread-de-chegada hospital pessoa)))
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-em-swap! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-mapv-extraida []
  "Simulação utilizando mapv para forçar a execução"
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [111, 222, 333, 444, 555, 666]
        starta (starta-thread-de-chegada hospital)]

    (mapv starta pessoas)

    (Thread/sleep 15000)
    (.start (Thread. (fn [] (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv-extraida)

(defn starta-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-em-swap! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-partial []
  "Simulação utilizando partial para preparar a função"
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [111, 222, 333, 444, 555, 666]
        starta (partial starta-thread-de-chegada hospital)]

    (mapv starta pessoas)

    (Thread/sleep 15000)
    (.start (Thread. (fn [] (pprint hospital))))))

(simula-um-dia-em-paralelo-com-partial)

(defn starta-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-em-swap! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-doseq []
  "Utilizar doseq para iterar por uma sequência de elementos"
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [111, 222, 333, 444, 555, 666]]

    (doseq [pessoa pessoas]
      (starta-thread-de-chegada hospital pessoa))

    (Thread/sleep 15000)
    (.start (Thread. (fn [] (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-doseq)


(defn simula-um-dia-em-paralelo-com-dotimes []
  "Utilizar dotimes para executar uma tarefa um número fixo de vezes"
  (let [hospital (atom (h.model/novo-hospital))]

    (dotimes [pessoa 6]
      (starta-thread-de-chegada hospital pessoa))

    (Thread/sleep 15000)
    (.start (Thread. (fn [] (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-dotimes)