(ns curso6.logic-test
  (:require [clojure.test :refer :all]
            [curso6.logic :refer :all]
            [curso6.model :as h.model]
            [schema.core :as s]))

(s/set-fn-validation! true)

(deftest cabe-na-fila?-test
  ;boundary tests
  (testing "Que cabe na fila quando o departamento está vazio"
    (is (cabe-na-fila? {:espera []} :espera)))
  (testing "Que cabe na fila quando departamento tem pessoas, mas não está cheio"
    (is (cabe-na-fila? {:espera [1 2]} :espera))
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera)))
  (testing "Que não cabe na fila quando departamento está cheio"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))
  (testing "Que não cabe na fila quando departamento mais do que cheio"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera))))
  (testing "Que retorna nil quando o departamento não existe"
    (is (nil? (cabe-na-fila? {:espera [1 2 3 4]} :raio-x)))))

(deftest chega-em-test
  (let [hospital-cheio {:espera [1 35 42 64 21]}]
    (testing "Aceita pessoas enquanto cabem pessoas no departamento"
      (is (= {:espera [1, 2, 3, 4, 5]}
             (chega-em {:espera [1, 2, 3, 4]}, :espera, 5)))
      (is (= {:espera [1, 2, 5]}
             (chega-em {:espera [1, 2]}, :espera, 5))))
    (testing "Não aceita pessoas quando não cabe no departamento"
      (is (thrown? IllegalStateException
                   (chega-em hospital-cheio, :espera, 6))))))

(deftest transfere-test
  (testing "Aceita pessoas se cabe"
    (let [hospital-original {:espera (conj h.model/fila-vazia "5"), :raio-x h.model/fila-vazia}]
      (is (= {:espera []
              :raio-x ["5"]}
             (transfere hospital-original :espera :raio-x))))
    (let [hospital-original {:espera (conj h.model/fila-vazia "51" "5"), :raio-x (conj h.model/fila-vazia "13")}]
      (is (= {:espera ["5"]
              :raio-x ["13" "51"]}
             (transfere hospital-original :espera :raio-x)))))
  (testing "Recusa pessoas se cabe"
    (let [hospital-cheio {:espera (conj h.model/fila-vazia "5")
                          :raio-x (conj h.model/fila-vazia "10" "20" "30" "40" "50")}]
      (is (thrown? IllegalStateException
             (transfere hospital-cheio :espera :raio-x)))))
  (testing "Não pode invocar transferência sem hospital"
    (is (thrown? clojure.lang.ExceptionInfo (transfere nil :espera :raio-x))))
  (testing "Condições obrigatórias"
    (let [hospital {:espera (conj h.model/fila-vazia "5"), :raio-x (conj h.model/fila-vazia "1", "54", "43", "12")}]
      (is (thrown? AssertionError (transfere hospital :nao-existe :raio-x)))
      (is (thrown? AssertionError (transfere hospital :raio-x :nao-existe))))))
