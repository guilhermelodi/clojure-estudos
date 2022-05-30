(ns alura-loja.aula1)

(println "def")
(println "abc")

(println (first ["daniela" "guilherme" "carlos" "paulo" "lucia"]))
;daniela

(println (rest ["daniela" "guilherme" "carlos" "paulo" "lucia"]))
;("guilherme" "carlos" "paulo" "lucia")

(println (rest []))
;()

(println (next ["daniela" "guilherme" "carlos" "paulo" "lucia"]))
;("guilherme" "carlos" "paulo" "lucia")

(println (next []))
;nil

;seq de um vetor vazio é nulo
(println (seq []))
;nil

(println (seq [1 2 3]))
;(1 2 3)

; implementação do curso
(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (meu-mapa funcao (rest sequencia))))))

; minha implementação
(defn meu-mapa
  [funcao sequencia]
  (if (first sequencia)
    (let [primeiro (first sequencia)]
      (funcao primeiro)
      (meu-mapa funcao (next sequencia)))))

(meu-mapa println ["daniela" "guilherme" "carlos" "paulo" "lucia"])
(meu-mapa println [])
(meu-mapa println nil)

; StackOverFlowError
;(meu-mapa println (range 6000))
; recursão pura tende a estourar a memória

; Tail recursion
; recur -> transforma a recursão em um laço otimizado
; recur precisa ser o "retorno" da função
(defn meu-mapa
  [funcao sequencia]
  (if (first sequencia)
    (let [primeiro (first sequencia)]
      (funcao primeiro)
      (recur funcao (next sequencia)))))

;(meu-mapa println (range 6000))