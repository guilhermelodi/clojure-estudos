(ns alura-estoque.aula2)

; Pure function
; Uma função que não depende de outro componente externo, apenas do seu argumento.

(defn valor-descontado
  "Retorna o valor com desconto de 10%."
  [valor-bruto]
  (let [desconto 0.10]
    (* valor-bruto (- 1 desconto))))

; let define um símbolo local
; recebe um vetor

; Adicionando novos símbolos dentro do let:

(defn valor-descontado
  "Retorna o valor com desconto de 10%."
  [valor-bruto]
  (let [taxa-de-desconto (/ 10 100)
        desconto         (* valor-bruto taxa-de-desconto)]
    (println "Calculando desconto de" desconto)
    (- valor-bruto desconto)))


; Condicional:

(if (> 500 100)
  (println "maior")           ;  se for true
  (println "menor ou igual")) ;  else opcional


; Adicionando if no método:

(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for maior que 100."
  [valor-bruto]
  (if (> valor-bruto 100)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (println "Calculando desconto de" desconto)
      (- valor-bruto desconto))
    valor-bruto))

(println (valor-descontado 95))

(println (valor-descontado 1000))

; PS: o nil(Nulo) é considerado false dentro do if