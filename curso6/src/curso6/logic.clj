(ns curso6.logic
  (:require [curso6.model :as h.model]
            [schema.core :as s]))

(defn cabe-na-fila?
  [hospital departamento]
  (some-> hospital
      departamento
      count
      (< 5)))

;(defn chega-em
;  [hospital departamento pessoa]
;  (if (cabe-na-fila? hospital departamento)
;    (update hospital departamento conj pessoa)
;    (throw (ex-info "não cabe ninguém neste departamento:" {:paciente pessoa}))))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (IllegalStateException. (str "não cabe ninguém neste departamento " departamento)))))

(s/defn atende :- h.model/Hospital
  [hospital :- h.model/Hospital
   departamento :- s/Keyword]
  (update hospital departamento pop))

(s/defn proxima :- h.model/PacienteID
  "Retorna o próximo paciente da fila"
  [hospital :- h.model/Hospital
   departamento :- s/Keyword]
  (-> hospital
      departamento
      peek))

(defn mesmo-tamanho? [hospital outro-hospital de para]
  (= (+ (count (get outro-hospital de)) (count (get outro-hospital para))
      (+ (count (get hospital de)) (count (get hospital para))))))

(s/defn transfere :- h.model/Hospital
  "Transfere o próximo paciente da fila 'de' para a fila 'para'"
  [hospital :- h.model/Hospital
   de :- s/Keyword
   para :- s/Keyword]
  ; em clojure é preferível ifs, whens, schemas, testes.
  ; ainda assim existem pre e post conditions
  {:pre  [(contains? hospital de)
          (contains? hospital para)]
   :post [(mesmo-tamanho? % hospital de para)]}
  (let [pessoa (proxima hospital de)]
    (-> hospital
      (atende de)
      (chega-em para pessoa))))