(ns curso5.aula1
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(defn adiciona-paciente [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

(defn adiciona-visita
  [visitas, paciente, novas-visitas]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(defn imprime-relatorio-de-paciente [visitas, paciente]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(defn testa-uso-de-pacientes []
  (let [guilherme {:id 15 :nome "Guilherme"}
        daniela {:id 20 :nome "Daniela"}
        paulo {:id 25 :nome "Paulo"}

        ; preenchimento com reduce
        pacientes (reduce adiciona-paciente {} [guilherme, daniela, paulo])

        ; variação com shadowing (fica estranho)
        visitas {}
        visitas (adiciona-visita visitas 15 ["01/01/2020"])
        visitas (adiciona-visita visitas 20 ["02/02/2020", "03/03/2020"])
        visitas (adiciona-visita visitas 15 ["04/04/2020"])]
    (pprint pacientes)
    (pprint visitas)
    (imprime-relatorio-de-paciente visitas daniela)))       ; executará a primeira vez, porém após a validação por schema, teremos um erro em tempo de *execução*


(testa-uso-de-pacientes)

(pprint (s/validate Long 15))
;(pprint (s/validate Long "guilherme"))                    ; clojure.lang.ExceptionInfo

; define que o schema será validado junto com a forma declarativa
(s/set-fn-validation! true)

; validação de forma declarativa
(s/defn teste-simples [x :- Long]
  (pprint x))

(teste-simples 30)
;(teste-simples "guilherme")                                 ;[(named (not (instance? java.lang.Long "guilherme")) x)]

(s/defn imprime-relatorio-de-paciente
  [visitas,
   paciente :- Long]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

;(testa-uso-de-pacientes)

(s/defn novo-paciente
  [id :- Long,
   nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 15 "Guilherme"))

;clojure.lang.ExceptionInfo
;[(named (not (instance? java.lang.Long "Guilherme")) id) (named (not (instance? java.lang.String 15)) nome)]
(pprint (novo-paciente "Guilherme" 15))