(ns alura-estoque.aula6)

(def pedido {:mochila  {:quantidade 2, :preco 10}
             :camiseta {:quantidade 5, :preco 40}})

(defn imprime-e-15 [valor]
  (println "valor" (class valor) valor)
  15)

(println (map imprime-e-15 pedido))
; RESULTADO:
;(valor clojure.lang.MapEntry [:mochila {:quantidade 2, :preco 10}]
;valor clojure.lang.MapEntry [:camiseta {:quantidade 5, :preco 40}]
;15 15)

; Destruct (desestruturar)
; [[]]
(defn imprime-e-15 [[chave valor]]
  (println "chave" chave "e" valor)
  15)

(println (map imprime-e-15 pedido))

(defn preco-dos-produtos [[chave valor]]
  (* (:quantidade valor) (:preco valor)))

(println (map preco-dos-produtos pedido))
(println "Total do pedido" (reduce + (map preco-dos-produtos pedido)))

; se não for utilizar um parâmetro da desestruturação, pode-se usar o underline
(defn preco-dos-produtos [[_ valor]]
  (* (:quantidade valor) (:preco valor)))

(println (map preco-dos-produtos pedido))
(println "Total do pedido" (reduce + (map preco-dos-produtos pedido)))

; Thread Last
(defn total-do-pedido
  [pedido]
  (->> pedido
       (map preco-dos-produtos)
       (reduce +)))

(println "Total do pedido" (total-do-pedido pedido))

(defn preco-total-do-produto [produto]
  (* (:quantidade produto) (:preco produto)))

; vals retorna os valoress
(defn total-do-pedido
  [pedido]
  (->> pedido
       vals
       (map preco-total-do-produto)
       (reduce +)))

(println "Total do pedido" (total-do-pedido pedido))


(println "\n\nFilter")

(def pedido {:mochila  {:quantidade 2, :preco 10}
             :camiseta {:quantidade 5, :preco 40}
             :brinde   {:quantidade 1}})

; criando a função com destruct
(defn gratuito?
  [[_ item]]
  (<= (get item :preco 0) 0))

(println "Filtrando gratuitos" (filter gratuito? pedido))

; criando a função com lambda
(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))

(println "Filtrando gratuitos" (filter (fn [[_ item]] (gratuito? item)) pedido))
(println "Filtrando gratuitos" (filter #(gratuito? (second %)) pedido))

(defn pago?
  [item]
  (not (gratuito? item)))

(println (pago? {:preco 50}))
(println (pago? {:preco 0}))

(println ((comp not gratuito?) {:preco 50}))

(def pago? (comp not gratuito?))
(println (pago? {:preco 50}))
(println (pago? {:preco 0}))

(def clientes [
               {:nome         "Guilherme"
                :certificados ["Clojure" "Java" "Machine Learning"]}
               {:nome         "Paulo"
                :certificados ["Java" "Ciência da Computação"]}
               {:nome         "Daniela"
                :certificados ["Arquitetura" "Gastronomia"]}])

; Calcule o total de certificados de todos os clientes

(println (->> clientes
              (map :certificados)
              (map count)
              (reduce +)))