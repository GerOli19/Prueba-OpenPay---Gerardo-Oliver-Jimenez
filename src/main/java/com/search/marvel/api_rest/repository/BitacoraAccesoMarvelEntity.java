package com.search.marvel.api_rest.repository;

import javax.persistence.*;

@Entity
public class BitacoraAccesoMarvelEntity {

    @Id
    @Column(name = "ID_BITACORA")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBitacora;

    @Column(name = "PATH")
    private String path;

    @Column(name = "HORA_CONSULTA")
    private String horaConsulta;

    /**
     * @return the idBitacora
     */
    public Long getIdBitacora() {
        return idBitacora;
    }

    /**
     * @param idBitacora the idBitacora to set
     */
    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the horaConsulta
     */
    public String getHoraConsulta() {
        return horaConsulta;
    }

    /**
     * @param horaConsulta the horaConsulta to set
     */
    public void setHoraConsulta(String horaConsulta) {
        this.horaConsulta = horaConsulta;
    }

}
