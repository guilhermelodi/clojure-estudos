(ns alura-estoque.aula3)

(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for maior que 100."
  [valor-bruto]
  (if (> valor-bruto 100)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println (valor-descontado 95))
(println (valor-descontado 1000))

; PREDICATE (finalizar com ? indica que retornará um boolean)
(defn aplica-desconto?
  [valor-bruto]
  (if (> valor-bruto 100)
    true))                                                  ; nao necessita de else porque nil é considera false

(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))

(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for maior que 100."
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println (valor-descontado 95))
(println (valor-descontado 1000))

; WHEN  - O when foi criado porque é comum apenas usar o if true
(defn aplica-desconto?
  [valor-bruto]
  (when (> valor-bruto 100)
    true))

; A versão final utilizará o conceito de quem a última linha de uma função é o retorno dela, logo:
(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

(println (valor-descontado 95))
(println (valor-descontado 1000))