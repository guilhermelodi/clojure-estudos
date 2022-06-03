(ns curso4.aula2
  (:use clojure.pprint))

;(defrecord Paciente [id, nome, nascimento])

; Paciente com Plano de Saúde -> + plano de saude
; Paciente Parcular -> sem alteraçao

(defrecord PacienteParticular [id, nome, nascimento])
(defrecord PacientePlanoDeSaude [id, nome, nascimento, plano])

;Particular -> se o valor é maior que 50 reais
;PlanoDeSaude -> procedimento fora do plano
;(defn deve-assinar-pre-autorizacao? [paciente procedimento valor]
;  (if (= PacienteParticular (type paciente))
;    (>= valor 50)
;    (if (= PacientePlanoDeSaude (type paciente))
;      (let [plano (get paciente :plano)]
;        (not (some #(= % procedimento) plano)))
;      true)))

; semelhante a interface
(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))

; alternativa seria implementar diretamente na definição do record
;(defrecord PacienteParticular
;  [id, nome, nascimento]
;  Cobravel
;  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
;    (>= valor 50)))

(let [particular (->PacienteParticular 1, "Guilherme", "01/01/2000")
      pacientePlano (->PacientePlanoDeSaude 15, "Daniela", "04/04/1980", [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 20))
  (pprint (deve-assinar-pre-autorizacao? particular, :ressonancia, 500))
  (pprint (deve-assinar-pre-autorizacao? pacientePlano, :raio-x, 20))
  (pprint (deve-assinar-pre-autorizacao? pacientePlano, :ressonancia, 500)))


(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(pprint (to-ms 56))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(pprint (to-ms (java.util.Date.)))


(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(pprint (to-ms (java.util.GregorianCalendar.)))