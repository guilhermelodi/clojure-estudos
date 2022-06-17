(ns curso5.aula2
  (:use clojure.pprint)
  (:require [schema.core :as s]))

; define que o schema será validado junto com a forma declarativa
(s/set-fn-validation! true)

;(s/defrecord Paciente
;  [id :- Long, nome :- s/Str])
;
;(pprint (Paciente. 15 "Guilherme"))
;(pprint (Paciente. "15" "Guilherme")) ; ainda assim aceita id como string

; chaves que são keywords no schema são obrigatórias
(def Paciente
  "Schema de um paciente"
  {:id s/Num, :nome s/Str})

(pprint (s/explain Paciente))
(pprint (s/validate Paciente {:id 15, :nome "Guilherme"}))

; cenário que falta um campo :nome
; Value does not match schema: {:nome missing-required-key, :description disallowed-key}
;(pprint (s/validate Paciente {:id 15, :description "Guilherme"}))

; cenário com campo a mais :plano
; Value does not match schema: {:plano disallowed-key}
;(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano [:raio-x]}))

(s/defn novo-paciente :- Paciente
  [id :- s/Num, nome :- s/Str]
  { :id id, :nome nome})

(pprint (novo-paciente 20 "Guilherme"))

; erro na construção do paciente de retorno
;(s/defn novo-paciente :- Paciente
;  [id :- s/Num, nome :- s/Str]
;  { :id id, :nome nome, :plano [:raio-x]})

; resultado:
; Output of novo-paciente does not match schema:
; {:plano disallowed-key}
(pprint (novo-paciente 20 "Guilherme"))

(defn estritamente-positivo? [x]
  (> x 0))

;(def EstritamentePositivo
;  (s/pred estritamente-positivo?))

(def EstritamentePositivo
  (s/pred estritamente-positivo? 'estritamente-positivo))

(pprint (s/validate EstritamentePositivo 15))
;(pprint (s/validate EstritamentePositivo 0))
;(pprint (s/validate EstritamentePositivo -15)) ;Value does not match schema: (not (estritamente-positivo -15))


(def Paciente
  "Schema de um paciente"
  {:id (s/constrained s/Int pos?), :nome s/Str})

(pprint (s/validate Paciente {:id 15, :nome "Guilherme"}))
;(pprint (s/validate Paciente {:id -15, :nome "Guilherme"}))

; lambda dentro dos schema são possíveis, porém atrapalha a legibilidade e não permite o teste da função separadamente
(def Paciente
  "Schema de um paciente"
  {:id (s/constrained s/Int #(> % 0)), :nome s/Str})

(pprint (s/validate Paciente {:id 15, :nome "Guilherme"}))
(pprint (s/validate Paciente {:id -15, :nome "Guilherme"}))