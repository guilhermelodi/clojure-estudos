(ns alura-estoque.aula1)

; command + shift + k = adiciona a próxima instrução
; command + shift + j = retira a próxima instrução

; Para chamada de métodos, usamos parênteses antes do método e os parâmetros são passados depois dos espaços:
(println "Hello World")

; Para definir variáveis, usamos os parênteses também, porém com a instrução def:
(def total-de-produtos 15)

; Mantendo sempre com os parâmetros, usamos o primeiro argumento (prefixo) como a operação que será efetuada, por exemplo uma soma de 13 com 3:
(+ 13 3)
(def total-de-produtos (+ total-de-produtos 3))
(println "Total" total-de-produtos)

; Para definir e manipular um vetor:
(def estoque ["Mochila", "Camiseta"])
(estoque 0) ; printa Mochila
(estoque 2) ; IndexOutOfBoundsException
(count estoque) ; 2 (tipo um length)
(conj estoque "Cadeira") ; o resultado do conj é "Mochila" "Camiseta" "Cadeira"
; porém o estoque não é alterado. O elemento cadeira não é adicionado no estoque que se mantém imutável
; para realmente adicionar a Cadeira temos:
(def estoque (conj estoque "Cadeira"))

; Definindo funções
; Uma das maneiras de definir uma função no Clojure é por meio do atalho defn.
; Padrão do Clojure é utilizar hífen ao invés de camelCase.
(defn imprime-mensagem []
  (println "------------")
  (println "Hello World"))

(defn aplica-deconto [valor-bruto]
  (* valor-bruto 0.9))