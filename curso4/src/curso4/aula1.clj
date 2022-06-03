(ns curso4.aula1
  (:use [clojure.pprint]))

(defn adiciona-paciente
  [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

(defn testa-adiciona-paciente []
  (let [pacientes {}
        paciente1 {:id 15 :nome "Guilherme" :nascimento "01/01/2000"}
        paciente2 {:id 20 :nome "Daniela" :nascimento "30/04/1980"}
        paciente3 {:nome "Paulo" :nascimento "15/10/2010"}]
    (pprint (adiciona-paciente pacientes paciente1))
    (pprint (adiciona-paciente pacientes paciente2))
    (pprint (adiciona-paciente pacientes paciente3))
    ))

;(testa-adiciona-paciente)

(defrecord Paciente [id, nome, nascimento])

(println (->Paciente 15 "Guilherme" "01/01/2000"))
(pprint (->Paciente 15 "Guilherme" "01/01/2000"))
(pprint (Paciente. 15 "Guilherme" "01/01/2000"))
(pprint (class (Paciente. 15 "Guilherme" "01/01/2000")))
(pprint (map->Paciente {:id 15, :nome "Guilherme", :nascimento "01/01/2000"}))

(pprint (assoc (Paciente. nil "Guilherme" "01/01/2000") :id 38))
(pprint (class (assoc (Paciente. nil "Guilherme" "01/01/2000") :id 38)))

; a classe criada é uma classe em Java
; é uma classe imutável
; é expansível
; tem os atributos obrigatórios com construtor
; tem os atributos opcionais com map->Paciente

(pprint (= (->Paciente 15 "Guilherme" "01/01/2000") (->Paciente 15 "Guilherme" "01/01/2000")))
(pprint (= (->Paciente 153 "Guilherme" "01/01/2000") (->Paciente 15 "Guilherme" "01/01/2000")))
(pprint (record? (->Paciente 15 "Guilherme" "01/01/2000")))
(pprint (.nome (->Paciente 15 "Guilherme" "01/01/2000")))
(pprint (.-nome (->Paciente 15 "Guilherme" "01/01/2000")))  ; buscar sobre esse hífen
