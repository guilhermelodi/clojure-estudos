(ns alura-estoque.aula5)

; Maps
(def estoque {"Mochila" 10 "Camiseta" 5})
(println estoque)

(def estoque {"Mochila" 10, "Camiseta" 5})

(println "Qtd:" (count estoque))
(println "Chaves: " (keys estoque))
(println "Valores: " (vals estoque))

; keyword
; :mochila
(def estoque {:mochila  10
              :camiseta 5})

(println estoque)

; add item no map
(println (assoc estoque :cadeira 20))
; assoc em uma key que já existe, atualiza ela
(println (assoc estoque :mochila 1))
(println estoque)

(println (update estoque :mochila dec))
(println (update estoque :mochila #(- % 8)))

; remove item
(println (dissoc estoque :camiseta))

(println "\n\n")

; mapa mais complexo
(def pedido {:mochila  {:quantidade 2, :preco 10}
             :camiseta {:quantidade 5, :preco 40}})

(println pedido)

(def pedido (assoc pedido :chaveiro {:quantidade 1, :preco 20}))

(println pedido)
; mapa como função
(println (pedido :mochila))
(println (get pedido :mochila))
(println (get pedido :cadeira))
; get como valor padrão
(println (get pedido :cadeira {}))
; keyword como função
(println (:mochila pedido))
(println (:cadeira pedido))
(println (:cadeira pedido {}))
; acessando uma key dentro de um mapa retornado por outra key
(println (:quantidade (:mochila pedido)))

(println (update-in pedido [:mochila :quantidade] inc))

; Threading ("fio")
; FORMA MAIS USADA
(println (-> pedido
             :mochila
             :quantidade))
