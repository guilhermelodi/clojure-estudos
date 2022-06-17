(ns curso5.aula3
  (:use clojure.pprint)
  (:require [schema.core :as s]))

; define que o schema será validado junto com a forma declarativa
(s/set-fn-validation! true)

(def PosInt (s/pred pos-int?))

(def Paciente
  "Schema de um paciente"
  { :id PosInt :nome s/Str})

(s/defn novo-paciente :- Paciente
  [id :- PosInt
   nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 15 "Guilherme"))
;(pprint (novo-paciente -15 "Guilherme"))

(defn maior-ou-igual-a-zero [x] (>= x 0))
(def ValorFinanceiro (s/constrained s/Num maior-ou-igual-a-zero))

(def Pedido
  {:paciente Paciente
   :valor ValorFinanceiro
   :procedimento s/Keyword})

(s/defn novo-pedido :- Pedido
  [paciente :- Paciente, valor :- ValorFinanceiro, procedimento :- s/Keyword]
  { :paciente paciente, :valor valor, :procedimento procedimento})

(pprint (novo-pedido (novo-paciente 15 "Guilherme") 25.5 :raio-x))

; schema de Plano com um vetor de keyword
(def Plano [s/Keyword])
(pprint (s/validate Plano [:raio-x]))

(def Paciente
  "Schema de um paciente"
  { :id PosInt :nome s/Str, :plano Plano})

(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano [:raio-x, :ultrassom]}))
(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano [:ultrassom]}))
(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano []}))
(pprint (s/validate Paciente {:id 15, :nome "Guilherme", :plano nil}))
;(pprint (s/validate Paciente {:id 15, :nome "Guilherme"}))
