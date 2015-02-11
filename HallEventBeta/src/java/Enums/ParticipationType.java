/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author thiagovulcao
 */
public enum ParticipationType {

    PARTICIPANTE,
    PALESTRANTE,
    ORGANIZADOR;

    @Override
    public String toString() {
        switch (this) {
            case PARTICIPANTE: {
                return "Participante";
            }
            case PALESTRANTE: {
                return "Palestrante";
            }
            case ORGANIZADOR: {
                return "Organizador";
            }
        }
        return null;
    }
}
