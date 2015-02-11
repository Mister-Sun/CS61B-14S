/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author dbjaime
 */
public enum RegisteredType {
    
    FORNECEDOR,
    COMPRADOR,
    VISITANTE,
    AGENTE,
    PRODUTOR;
    
    @Override
    public String toString() {
        switch (this) {
            case FORNECEDOR: {
                return "FORNECEDOR";
            }
            case COMPRADOR: {
                return "COMPRADOR";
            }
            case VISITANTE: {
                return "VISITANTE";
            }
            case AGENTE: {
                return "AGENTE DE DESENVOLVIMENTO";
            }
            case PRODUTOR: {
                return "PRODUTOR RURAL";
            }
        }
        return null;
    }
}
