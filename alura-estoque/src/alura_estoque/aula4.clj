(ns alura-estoque.aula4)

(def precos [30 700 1000.0])

(println (precos 0))
(println (get precos 0))
(println (get precos 17))
(println (get precos 17 "valor padrão"))

(println (update precos 0 inc))                             ; lembrando que devolve um vetor novo (imutabilidade)
(println (update precos 2 dec))                             ; lembrando que devolve um vetor novo (imutabilidade)

(defn divide-3
  [valor]
  (/ valor 3))

(println (update precos 2 divide-3))

(println
  (type
    (get (update precos 2 divide-3) 2)))


; código da aula anterior

(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for maior que 100."
  [valor-bruto]
  (println "executando valor-descontado para o valor" valor-bruto)
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))


; MAP
(println "executa a função para cada item" (map valor-descontado precos))

; FILTER
(println (range 10))                                        ; cria um vetor até 10 (n) exclusivo
(println (filter even? (range 10)))

(println (filter aplica-desconto? precos))

; REDUCE
(println (reduce + precos))                                 ; executa a função + para todos os itens

(defn minha-soma
  [valor-1 valor-2]
  (println "somando" valor-1 valor-2)
  (+ valor-1 valor-2))

(println (reduce minha-soma precos))
; através dos printlns dá para perceber que o reduce pega o primeiro elemento e executa a função com o próximo elemento.
; E com o resultado, executa com o próximo elemento e assim por diante.

(println (reduce minha-soma 50 precos))
; nesse exemplo, o 50 é o valor inicial. Com isso, a lógica fica 50 e o primeiro elemento. Daí utiliza o resultado com o segundo elemento...