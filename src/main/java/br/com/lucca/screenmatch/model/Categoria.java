package br.com.lucca.screenmatch.model;

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    ANIMACAO("Animation", "Animação"),
    CRIME("Crime", "Crime");

    private String categoriaOmdb;
    private String categoriaPT;

    Categoria(String categoriaOmdb, String categoriaPT) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPT = categoriaPT;
    }

    public static Categoria fromString (String text){
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromPortugues (String text){
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPT.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

}
