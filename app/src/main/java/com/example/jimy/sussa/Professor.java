package com.example.jimy.sussa;

/**
 * Created by jimy on 28/06/16.
 */
public class Professor {
    String nome;
    int imagesrc;
    int ratingDidatica, ratingCoerencia, ratingDominio, ratingAuxilio, votos;

    public Professor(String nome, int imagesrc) {
        this.nome = nome;
        this.imagesrc = imagesrc;
        ratingDidatica = 3;
        ratingCoerencia = 3;
        ratingDominio = 3;
        ratingAuxilio = 3;
        votos = 0;
    }

    public int getRatingMedio()
    {
        return (ratingAuxilio+ratingDidatica+ratingCoerencia+ratingDominio)/4;

    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getImagesrc() {
        return imagesrc;
    }

    public void setImagesrc(int imagesrc) {
        this.imagesrc = imagesrc;
    }

    public int getRatingDidatica() {
        return ratingDidatica;
    }

    public void setRatingDidatica(int ratingDidatica) {
        this.ratingDidatica = ratingDidatica;
    }

    public int getRatingCoerencia() {
        return ratingCoerencia;
    }

    public void setRatingCoerencia(int ratingCoerencia) {
        this.ratingCoerencia = ratingCoerencia;
    }

    public int getRatingDominio() {
        return ratingDominio;
    }

    public void setRatingDominio(int ratingDominio) {
        this.ratingDominio = ratingDominio;
    }

    public int getRatingAuxilio() {
        return ratingAuxilio;
    }

    public void setRatingAuxilio(int ratingAuxilio) {
        this.ratingAuxilio = ratingAuxilio;
    }

    public void votarDidatica(int rating)
    {
        votos++;
        ratingDidatica = (ratingDidatica+rating)/votos;
    }
    public void votarCoerencia(int rating)
    {
        votos++;
        ratingCoerencia = (ratingCoerencia+rating)/votos;
    }
    public void votarDominio(int rating)
    {
        votos++;
        ratingDominio = (ratingDominio+rating)/votos;
    }
    public void votarAuxilio(int rating)
    {
        votos++;
        ratingAuxilio = (ratingAuxilio+rating)/votos;
    }
}
