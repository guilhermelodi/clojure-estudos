(ns curso3.aula5
  (:use [clojure.pprint])
  (:require [curso3.logic :as h.logic]
            [curso3.model :as h.model]))

(defn chega-em-espera!
  [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa))

(defn transfere!
  [hospital sala-origem sala-destino]
  (swap! hospital h.logic/transfere sala-origem sala-destino))

(defn simula-um-dia []
  (let [hospital (atom (h.model/novo-hospital))]
    (chega-em-espera! hospital 111)
    (chega-em-espera! hospital 222)
    (chega-em-espera! hospital 333)
    (chega-em-espera! hospital 444)
    (transfere! hospital :espera :laboratorio1)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :laboratorio2 :laboratorio3)
    (pprint hospital)))

(simula-um-dia)