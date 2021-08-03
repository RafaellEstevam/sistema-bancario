package br.com.alura.banco.model;

/**
 * @author Rafaell Estevam
 *
 */
public class MediaDataDTO {

	private Double media;
	private Integer dia;
	private Integer mes;
	private Integer ano;

	public MediaDataDTO(Double media, Integer dia, Integer mes, Integer ano) {
		super();
		this.media = media;
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
	}

	public Double getMedia() {
		return media;
	}

	public void setMedia(Double media) {
		this.media = media;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

}
