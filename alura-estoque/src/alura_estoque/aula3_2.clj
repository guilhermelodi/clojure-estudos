(ns alura-estoque.aula3-2)

; HIGHER ORDER FUNCTIONS
; Trocando a função para algo mais simbólico
(defn mais-caro-que-100?
  [valor-bruto]
  (> valor-bruto 100))

; Na nova versão, receberemos a flag se deve ou não aplicar desconto
(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for maior que 100."
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println (valor-descontado mais-caro-que-100? 95))
(println (valor-descontado mais-caro-que-100? 1000))

; Função anônima
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 95))
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 1000))
(println (valor-descontado (fn [v] (> v 100)) 95))
(println (valor-descontado (fn [v] (> v 100)) 1000))
(println (valor-descontado #(> %1 100) 95))
(println (valor-descontado #(> %1 100) 1000))
; lambda
(println (valor-descontado #(> % 100) 95))                  ; menor, porém muito mais complexo
(println (valor-descontado #(> % 100) 1000))                ; menor, porém muito mais complexo

(def valor-atraves-de-funcao? (fn [valor-bruto] (> valor-bruto 100)))
(def valor-atraves-de-funcao? #(> % 100))
(println (valor-atraves-de-funcao? 150))
