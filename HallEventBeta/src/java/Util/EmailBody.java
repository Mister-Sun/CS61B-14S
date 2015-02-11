 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Entity.Registered;
import Entity.Event_Registered;
import Entity.Person;
import java.io.Serializable;

/**
 *
 * @author Thiago
 */
public class EmailBody implements Serializable {

    private static String mensagem;

    public static String getFreeEvents(Person person) {
        String table = "";
        String temp = "";
        Registered i = person.getRegistered().get(0);
        boolean check = false;
        if (!i.getEventRegisteredList().isEmpty()) {

            for (Event_Registered p : i.getEventRegisteredList()) {
                if (p.getEvent().isFree()) {
                    check = true;
                    temp += "<tr><td>" + p.getEvent().getEventDateToEmail() + "</td><td>" + p.getEvent().getName() + "</td><td>EVENTO GRATUITO</td></tr>";

                }
            }
            if (check) {
                table += "<p>At&eacute; este momento voc&ecirc; est&aacute; pr&eacute;-inscrito nos seguintes eventos:</p>"
                        + "<table align=\"center\" border=\"1\" cellpadding=\"3\" cellspacing=\"0\" style=\"width:650px\"><thead><tr>"
                        + "<th scope=\"col\">HORA</th><th scope=\"col\">EVENTO</th><th scope=\"col\">PAGAMENTO</th></tr></thead><tbody>";
                table += temp;
            } else {
                table += "Voce não esta inscrito em nenhum evento gratuito até o momento.";
            }
            table += "</tbody></table>";
        } else {
            //table += "Voce não esta inscrito em nenhum evento pago até o momento.";
        }
        return table;
    }

    public static String getPaidEvents(Person person) {
        String table = "";
        String temp = "";
        Registered i = person.getRegistered().get(0);
        boolean check = false;
        if (!i.getEventRegisteredList().isEmpty()) {

            for (Event_Registered p : i.getEventRegisteredList()) {
                if (!p.getEvent().isFree()) {
                    check = true;
                    temp += "<tr><td>" + p.getEvent().getEventDateToEmail() + "</td><td>" + p.getEvent().getName() + "</td>";
                    p.getEvent().setPagSeg("cadastrar o codigo do link");
                    temp += "<td><form action=\"https://pagseguro.uol.com.br/checkout/v2/cart.html?action=add\" method=\"post\">\n"
                            + "        <input type=\"hidden\" name=\"itemCode\" value=\"" + p.getEvent().getPagSeg() + "\" />\n"
                            + "        <input type=\"image\" src=\"https://p.simg.uol.com.br/out/pagseguro/i/botoes/pagamentos/209x48-comprar-azul-assina.gif\" name=\"submit\" alt=\"Pague com PagSeguro - Ã© rÃ¡pido, grÃ¡tis e seguro!\" />\n"
                            + "        </form></td></tr>";
                }
            }
            if (check) {
                table += "<p>At&eacute; este momento voc&ecirc; est&aacute; pr&eacute;-inscrito nos seguintes eventos:</p>"
                        + "<table align=\"center\" border=\"1\" cellpadding=\"3\" cellspacing=\"0\" style=\"width:650px\"><thead><tr>"
                        + "<th scope=\"col\">HORA</th><th scope=\"col\">EVENTO</th><th scope=\"col\">PAGAMENTO</th></tr></thead><tbody>";
                table += temp;
            } else {
                table += "Voce não esta inscrito em nenhum evento pago até o momento.";
            }
            table += "</tbody></table>";
        } else {
            //table += "Voce não esta inscrito em nenhum evento pago até o momento.";
        }
        return table;
    }

    public static String getConfirmationMessage(Person i) {
        mensagem = "<div style=\"width: 700px;font-family:Verdana, Geneva, sans-serif\">"
                + "<div id=\"topo\" align=\"center\" >"
                + "<img src=\"http://ebiinfo.com.br/compamostra/compamostralogo.png\" />"
                + "</div>"
                + "<p style=\"text-align: justify;\"><strong>Prezado(a) Senhor(a), " + i.getIndividual().getName() + "</strong></p>"
                + "<p style=\"text-align: justify;\"><strong>Recebemos sua solicitação de pr&eacute;-inscri&ccedil;&atildeo, por&eacute;m para que a mesma seja conclu&iacute;da, &eacute; necess&aacaute;rio que seja feito o pagamento imediatamente utilizando pagSeguro, nos links abaixo.</strong></p>"
                + "<p style=\"text-align: justify;\">Informamos ainda que <strong>X Computa&ccedil;&atilde;o Amostra</strong> acontecer&aacute; no per&iacute;odo de 27 a 30/5/2014 no CESUPA Centro Universit&aacute;rio do Par&aacute;, localizado na Av. Gov. Jos&eacute; Malcher n. 1963 - CEP 66060-230, Fone: (91) 4009-9100.</p>"
                + getPaidEvents(i)
                + getFreeEvents(i)
                //                + "<p style=\"text-align: justify;\"><strong>Informa&ccedil;&otilde;es Importantes:</strong></p>"
                //                + "<ol>"
                //                + "	<li style=\"text-align: justify;\">Verifique na programa&ccedil;&atilde;o o tipo de evento disponibilizado (P&uacute;blico ou Restrito).</li>"
                //                + "</ol>"
                //                + "<ul>"
                //                + "	<li style=\"text-align: justify;\">Os eventos do <strong>Tipo P&uacute;blico</strong> podem ter a participa&ccedil;&atilde;o do p&uacute;blico em geral.</li>"
                //                + "	<li style=\"text-align: justify;\">Os eventos do <strong>Tipo Restrito</strong> foram formatados para p&uacute;blicos espec&iacute;ficos, preferencialmente, Clientes SEBRAE, Empres&aacute;rios, propriet&aacute;rios ou s&oacute;cios de ME ou EPP, MEI ou potencial empres&aacute;rio.</li>"
                //                + "</ul>"
                //                + "<ol>"
                //                + "	<li style=\"text-align: justify;\">Esteja no local do evento com, pelo menos, 30min. de anteced&ecirc;ncia, portando Carteira de Identidade e Comprovante de Inscri&ccedil;&atilde;o.</li>"
                //                + "	<li style=\"text-align: justify;\">Obter&atilde;o Certificados ou Declara&ccedil;&otilde;es os participantes que obtiverem frequ&ecirc;ncia de, no m&iacute;nimo, 90% de participa&ccedil;&atilde;o no evento para o qual est&aacute; inscrito.</li>"
                //                + "	<li style=\"text-align: justify;\">Certificados e/ou Declara&ccedil;&otilde;es ser&atilde;o emitidos para Palestrantes/Facilitadores e Participantes, &nbsp;atrav&eacute;s do site, 10 dias ap&oacute;s o termino do evento.</li>"
                //                + "	<li style=\"text-align: justify;\">N&atilde;o ser&aacute; permitido o acesso de crian&ccedil;as ou adolescentes.</li>"
                //                + "</ol>"
                //                + "<h3>O <span style=\"color:#0099ff\"><strong>SEBRAE Par&aacute;</strong></span> agradece a sua participa&ccedil;&atilde;o!</h3>"
                + "<div id=\"footer\" align=\"center\">"
                + "<hr />"
                + "<img src=\"http://ebiinfo.com.br/compamostra/rodape.png\" align=\"middle\" style=\"height:106px; width:650px\" />"
                + "</div></div>";

        return mensagem;
    }

    public static String getConfirmationFomenta(Person person) {
        mensagem = "<div style=\"width: 700px;font-family:Verdana, Geneva, sans-serif\">"
                + "<div id=\"topo\" align=\"center\" >"
                + "<img alt=\"\" src=\"http://www.fomentapa.com.br/2014/images/fmt-utils/logo-p.png\" style=\"height:122px; width:301px\" />"
                + "</div>"
                + "<p style=\"text-align: center;\"><strong>Bel&eacute;m, 17 e 18 de Setembro de 2014</strong><br />"
                + "<strong>Hangar - Centro de Conven&ccedil;&otilde;es e Feiras da Amaz&ocirc;nia</strong><br />"
                + "<strong>Encontro de Oportunidades para Micro e Pequenas Empresas nas Compras Governamentais</strong></p>"
                + "<p>Seja bem vindo ao Fomenta Par&aacute;&nbsp;2014!</p>"
                + "<p>Informamos que seu cadastro foi realizado com sucesso, consulte as informa&ccedil;&otilde;es abaixo:</p>"
                + "<table align=\"left\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">"
                + "<tbody><tr>"
                + "<td><strong>NOME:</strong></td>"
                + "<td>" + person.getIndividual().getName() + "</td></tr><tr>"
                + "<td><strong>CPF:</strong></td>"
                + "<td>" + person.getIndividual().getCpf() + "</td></tr>"
                + "<tr>"
                + "<td><strong>E-MAIL:</strong></td>"
                + "<td>" + person.getLogin().getUsername() + "</td></tr></tbody></table>"
                + "<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>\n"
                + "<p>Acesse o site para mais informa&ccedil;&otilde;es.&nbsp;</p>"
                + "<p><a href=\"http://www.fomentapa.com.br/\">http://www.fomentapa.com.br/</a></p>\n"
                + "<p>0800 570 0800&nbsp;</p></div>";
        return mensagem;
    }
}
