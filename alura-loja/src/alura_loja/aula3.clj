(ns alura-loja.aula3
  (:require [alura-loja.db :as l.db]))

(println (l.db/todos-os-pedidos))

(println (group-by :usuario (l.db/todos-os-pedidos)))

(defn minha-funcao-de-agrupamento
  [elemento]
  (println "elemento" elemento)
  (:usuario elemento))

(println (group-by minha-funcao-de-agrupamento (l.db/todos-os-pedidos)))
;{15 [{:usuario 15, :itens {:mochila {:id :mochila, :quantidade 2, :preco-unitario 10}, :camiseta {:id :camiseta, :quantidade 3, :preco-unitario 40}, :tenis {:id :tenis, :quantidade 1}}} {:usuario 15, :itens {:mochila {:id :mochila, :quantidade 1, :preco-unitario 10}, :camiseta {:id :camiseta, :quantidade 1, :preco-unitario 40}, :tenis {:id :tenis, :quantidade 1}}} {:usuario 15, :itens {:mochila {:id :mochila, :quantidade 20, :preco-unitario 10}, :camiseta {:id :camiseta, :quantidade 3, :preco-unitario 40}, :tenis {:id :tenis, :quantidade 1}}}],
; 1 [{:usuario 1, :itens {:mochila {:id :mochila, :quantidade 2, :preco-unitario 10}, :camiseta {:id :camiseta, :quantidade 3, :preco-unitario 40}, :tenis {:id :tenis, :quantidade 1}}} {:usuario 1, :itens {:mochila {:id :mochila, :quantidade 1, :preco-unitario 10}, :camiseta {:id :camiseta, :quantidade 1, :preco-unitario 40}, :tenis {:id :tenis, :quantidade 1}}}],
; 20 [{:usuario 20, :itens {:mochila {:id :mochila, :quantidade 2, :preco-unitario 10}, :camiseta {:id :camiseta, :quantidade 10, :preco-unitario 40}, :tenis {:id :tenis, :quantidade 1}}}]}

(println (map count (vals (group-by :usuario (l.db/todos-os-pedidos)))))
;(3 2 1)

(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     vals
     (map count)
     println)
;(3 2 1)
;contou a quantidade de pedidos por usuário, mas não retornou o id

(defn conta-total-por-usuario
  [[_ pedidos]]
  (count pedidos))

(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)
;(3 2 1)

(defn conta-total-por-usuario
  [[usuario pedidos]]
  {:id-usuario       usuario
   :total-de-pedidos (count pedidos)})

(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)
;({:id-usuario 15, :total-de-pedidos 3} {:id-usuario 1, :total-de-pedidos 2} {:id-usuario 20, :total-de-pedidos 1})

;ex: adicionar valor total de todos os pedidos por usuário
(defn total-do-item
  [[_ detalhes]]
  (* (get detalhes :quantidade 0) (get detalhes :preco-unitario 0)))

(defn total-do-pedido
  [pedido]
  (reduce + (map total-do-item pedido)))

(defn total-dos-pedidos
  [pedidos]
  (->> pedidos
       (map :itens)
       (map total-do-pedido)
       (reduce +)))

(defn quantia-de-pedidos-e-gasto-total-por-usuario
  [[usuario pedidos]]
  {:id-usuario       usuario
   :total-de-pedidos (count pedidos)
   :preco-total (total-dos-pedidos pedidos)})

(println "executando quantia e gasto total")
(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map quantia-de-pedidos-e-gasto-total-por-usuario)
     println)