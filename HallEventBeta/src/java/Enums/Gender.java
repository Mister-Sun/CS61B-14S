/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author thiagovulcao
 */
public enum Gender {

    MASCULINO,
    FEMININO;

    @Override
    public String toString() {
        switch (this) {
            case MASCULINO:
                return "Masculino";
            case FEMININO:
                return "Feminino";
            default:
                return null;
        }
    }
}
