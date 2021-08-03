package br.com.alura.banco.model;

/**
 * @author Rafaell Estevam
 *
 */
public class MediaContaDTO {

	private Double media;
	private Conta conta;

	public MediaContaDTO(Double media, Conta conta) {
		super();
		this.media = media;
		this.conta = conta;
	}

	public Double getMedia() {
		return media;
	}

	public void setMedia(Double media) {
		this.media = media;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}
