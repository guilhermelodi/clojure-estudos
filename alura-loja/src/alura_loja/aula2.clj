(ns alura-loja.aula2)

;["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]

(defn conta
  [total-ate-agora elementos]
  (if (seq elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta 0 nil))
(println (conta 0 []))

; tipo de polimorfismo (por quantidade de parâmetro)
(defn minha-funcao
  ([param1] (println "p1" param1))
  ([param1 param2] (println "p2" param1 param2)))

(minha-funcao "abc")
(minha-funcao "abc" "def")

(defn conta
  ([elementos]
    (conta 0 elementos))

  ([total-ate-agora elementos]
  (if (seq elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora)))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta 0 nil))
(println (conta 0 []))
(println (conta ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta nil))
(println (conta []))


; variação com loop
(println "resultado com loop")
(defn conta
  [elementos]
  (loop [total-ate-agora 0
         elementos-restantes elementos]
    (if (seq elementos-restantes)
      (recur (inc total-ate-agora) (next elementos-restantes))
      total-ate-agora)))

(println (conta ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta nil))
(println (conta []))

; Loop em geral parece ser um code smell uma vez que transparece controle de fluxo que poderia ter sido isolado em uma função.