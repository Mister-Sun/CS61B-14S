/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enums;

/**
 *
 * @author EBI
 */
public enum Cards {
    
    VISA,
    MASTER,
    DINNERS;

    @Override
    public String toString() {
        switch (this) {
            case VISA:
                return "VISA";
            case MASTER:
                return "MASTERCARD";
            case DINNERS:
                return "DINNERSCLUB";
            default:
                return null;
        }
    }
}
