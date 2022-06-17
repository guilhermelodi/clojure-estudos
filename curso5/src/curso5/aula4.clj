(ns curso5.aula4
  (:use clojure.pprint)
  (:require [schema.core :as s]))

; define que o schema será validado junto com a forma declarativa
(s/set-fn-validation! true)

(def PosInt (s/pred pos-int?))

; schema de Plano com um vetor de keyword
(def Plano [s/Keyword])

(def Paciente
  "Schema de um paciente"
  {:id                          PosInt
   :nome                        s/Str
   :plano                       Plano
   (s/optional-key :nascimento) s/Str})

(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano [:raio-x, :ultrassom]}))
(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano [:ultrassom]}))
(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano []}))
(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano nil}))
(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano [], :nascimento "01/09/2000"}))

; Pacientes
; { 15 {PACIENTE} 32 {PACIENTE} }

(def Pacientes
  {PosInt Paciente})

(pprint (s/validate Pacientes {}))
(let [guilherme {:id 15, :nome "Guilherme", :plano [:raio-x]}]
  (pprint (s/validate Pacientes {15 guilherme})))
  ;(pprint (s/validate Pacientes {-15 guilherme}))
  ;(pprint (s/validate Pacientes {-15 15}))
  ;(pprint (s/validate Pacientes {-15 {:id 15, :nome "Guilherme"}}))

(def Visitas
  {PosInt [s/Str]})
