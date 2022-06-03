(ns curso4.aula4
  (:use clojure.pprint))

(defrecord PacienteParticular [id, nome, nascimento, situacao])
(defrecord PacientePlanoDeSaude [id, nome, nascimento, situacao, plano])

(defn nao-eh-urgente? [paciente]
  (not= :urgente (:situacao paciente :normal)))

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (and (>= valor 50) (nao-eh-urgente? paciente))))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (and (not (some #(= % procedimento) plano)) (nao-eh-urgente? paciente)))))

(let [particular (->PacienteParticular 1, "Guilherme", "01/01/2000", :normal)
      pacientePlano (->PacientePlanoDeSaude 15, "Daniela", "04/04/1980", :normal, [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 20))
  (pprint (deve-assinar-pre-autorizacao? particular, :ressonancia, 500))
  (pprint (deve-assinar-pre-autorizacao? pacientePlano, :raio-x, 20))
  (pprint (deve-assinar-pre-autorizacao? pacientePlano, :ressonancia, 500)))

(let [particular (->PacienteParticular 1, "Guilherme", "01/01/2000", :urgente)
      pacientePlano (->PacientePlanoDeSaude 15, "Daniela", "04/04/1980", :urgente, [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 20))
  (pprint (deve-assinar-pre-autorizacao? particular, :ressonancia, 500))
  (pprint (deve-assinar-pre-autorizacao? pacientePlano, :raio-x, 20))
  (pprint (deve-assinar-pre-autorizacao? pacientePlano, :ressonancia, 500)))

; apenas está com multi no final para comparar depois
;(defmulti deve-assinar-pre-autorizacao-multi? class)
;(defmethod deve-assinar-pre-autorizacao-multi? PacienteParticular [paciente]
;  true)
;(defmethod deve-assinar-pre-autorizacao-multi? PacientePlanoDeSaude [paciente]
;  false)
;
;(let [particular (->PacienteParticular 1, "Guilherme", "01/01/2000", :urgente)
;      pacientePlano (->PacientePlanoDeSaude 15, "Daniela", "04/04/1980", :urgente, [:raio-x, :ultrassom])]
;  (pprint (deve-assinar-pre-autorizacao-multi? particular))
;  (pprint (deve-assinar-pre-autorizacao-multi? particular))
;  (pprint (deve-assinar-pre-autorizacao-multi? pacientePlano))
;  (pprint (deve-assinar-pre-autorizacao-multi? pacientePlano)))

; pedido { :paciente paciente, :valor valor, :procedimento procedimento }
(defn tipo-de-autorizador [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)
        urgencia? (= :urgencia situacao)]
    (if urgencia?
      :sempre-autorizado
      (class paciente))))
; nessa função não está 100% porque estamos misturando keyword com classe

(defmulti deve-assinar-pre-autorizacao-multi? tipo-de-autorizador)

(defmethod deve-assinar-pre-autorizacao-multi? :sempre-autorizado [pedido]
  false)

(defmethod deve-assinar-pre-autorizacao-multi? PacienteParticular [pedido]
  (>= (:valor pedido) 50))

(defmethod deve-assinar-pre-autorizacao-multi? PacientePlanoDeSaude [pedido]
  (let [paciente (:paciente pedido)
        plano (:plano paciente)
        procedimento (:procedimento pedido)]
    (not (some #(= % procedimento) plano))))

(pprint "iniciando teste")
(let [particular (->PacienteParticular 1, "Guilherme", "01/01/2000", :normal)
      pacientePlano (->PacientePlanoDeSaude 15, "Daniela", "04/04/1980", :normal, [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao-multi? {:paciente particular, :valor 20, :procedimento :coleta-de-sange}))
  (pprint (deve-assinar-pre-autorizacao-multi? {:paciente particular, :valor 500, :procedimento :ressonancia}))
  (pprint (deve-assinar-pre-autorizacao-multi? {:paciente pacientePlano, :valor 20, :procedimento :raio-x}))
  (pprint (deve-assinar-pre-autorizacao-multi? {:paciente pacientePlano, :valor 500, :procedimento :ressonancia})))

(let [particular (->PacienteParticular 1, "Guilherme", "01/01/2000", :urgencia)
      pacientePlano (->PacientePlanoDeSaude 15, "Daniela", "04/04/1980", :urgencia, [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao-multi? {:paciente particular, :valor 20, :procedimento :coleta-de-sange}))
  (pprint (deve-assinar-pre-autorizacao-multi? {:paciente particular, :valor 500, :procedimento :ressonancia}))
  (pprint (deve-assinar-pre-autorizacao-multi? {:paciente pacientePlano, :valor 20, :procedimento :raio-x}))
  (pprint (deve-assinar-pre-autorizacao-multi? {:paciente pacientePlano, :valor 500, :procedimento :ressonancia})))