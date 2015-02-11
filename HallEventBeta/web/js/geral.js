//------------------------------------------------------------------------------------------------------------
// Fun��o para n�o permitir entrada texto.
// Exemplo: <h:inputText onkeydown="return campoLeitura(event);" onkeypress="return campoLeitura(event);" />
// Colocar no atributo onkeypress e onkeypress do bot�o.
//------------------------------------------------------------------------------------------------------------
function campoLeitura(e) {

    var keychar;
    if (window.event) { // IE
        keychar = e.keyCode;
    } else if (e.which || e.which == 0) { // Netscape/Firefox/Opera
        keychar = e.which;
    }
    if (keychar == 0 || keychar == 9 || keychar == 13) {
        return true;
    } else {
        return false;
    }
}

function getAllElementsByName(tag, name) {
    var elems = document.getElementsByTagName(tag);
    var arr = new Array();

    for (var i = 0; i < elems.length; i++) {
        var elem = elems[i];

        if (elem.getAttribute('name') === name) {
            arr.push(elem);
        }
    }
    return arr;
}

function lovChamada(arg0) {
    var str = null;
    var lovParam = 'lovCall=true';
    if (arg0.indexOf('?') === -1)
        str = arg0 + '?' + lovParam;
    else
        str = arg0 + '&' + lovParam;

    var altura = 500; //420
    var largura = 701;
    var x = parseInt((screen.width - largura) / 2);
    var y = parseInt((screen.height - altura) / 2);
    var win = window.open(str, 'lov', 'width=' + largura + ',height=' + altura + 'top=0,left=0,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=no,copyhistory=no');
    win.moveTo(x, y);
//window.open(str,'popup','width=701,height=420,top=0,left=0,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=no,copyhistory=no');
}

function money(campo){
    
    var x = campo;
    
    c1 = x.value;
    
    var charpos = c1.search("[^0-9-.-, ]");
    alert("erro");
    if (c1.length > 0 && charpos >= 0){
        
        alert("Você não pode digitar Letras e virgulas neste Campo.");
        campo.value = c1.substring(charpos, "");
        return false;
    }else{
        
    }
}

function SomenteNumero(e) {
    alert("Você não pode digitar Letras e virgulas neste Campo.");
    var tecla = (window.event) ? event.keyCode : e.which;
    if ((tecla > 47 && tecla < 58)){
        return true; // numeros de 0 a 9    
    }else {
        if (tecla !== 8){
            return false; //backspace
        }else{
            return true;
        }
    }
}

function money(e){
    var tecla=(window.event)?event.keyCode:e.which;   
    if((tecla>47 && tecla<58)) return true;
    else{
    	if (tecla===8 || tecla===0 || tecla === 46) return true;
	else  return false;
    }
}

function SomenteLetras(e) {
    var tecla = (window.event) ? event.keyCode : e.which;
    //alert(tecla);
    //return;
    if (tecla > 65 && tecla < 90) // LETRAS MAIUSCULAS
        return true;
    else
    if (tecla >= 97 && tecla <= 122) // LETRAS MINUSCULAS
        return true;
    {
        if (tecla !== 32) // barra de espa�o
            return false;
        else
            return true;
        if (tecla !== 8) // backspace
            return false;
        else
            return true;
    }
}

function notEspPont(campo) {
    var x = campo;
    c1 = x.value;

    var charpos = c1.search("[^A-Za-z-0-9- ]");
    if (c1.length > 0 && charpos >= 0)
    {
        alert("Você não pode digitar [Caracteres especiais] e [Pontos] neste Campo.");
        campo.value = c1.substring(charpos, "");
        return false;
    }
}

function FormataCpf(campo, teclapres)
{
    var tecla = teclapres.keyCode;
    var vr = new String(campo.value);
    vr = vr.replace(".", "");
    vr = vr.replace("/", "");
    vr = vr.replace("-", "");
    tam = vr.length + 1;
    if (tecla != 14)
    {
        if (tam == 4)
            campo.value = vr.substr(0, 3) + '.';
        if (tam == 7)
            campo.value = vr.substr(0, 3) + '.' + vr.substr(3, 6) + '.';
        if (tam == 11)
            campo.value = vr.substr(0, 3) + '.' + vr.substr(3, 3) + '.' + vr.substr(7, 3) + '-' + vr.substr(11, 2);
    }
}

function Mascara(formato, keypress, objeto) {
    campo = eval(objeto);
    // cep
    if (formato == 'cep') {
        separador = '-';
        conjunto1 = 5;
        if (campo.value.length == conjunto1) {
            campo.value = campo.value + separador;
        }
    }

    // nascimento
    if (formato == 'nascimento') {
        separador = '/';
        conjunto1 = 2;
        conjunto2 = 5;
        if (campo.value.length == conjunto1)
        {
            campo.value = campo.value + separador;
        }
        if (campo.value.length == conjunto2)
        {
            campo.value = campo.value + separador;
        }
    }

    // telefone
    if (formato == 'telefone') {
        separador1 = '(';
        separador2 = ')';
        separador3 = '-';
        conjunto1 = 0;
        conjunto2 = 3;
        conjunto3 = 8;
        if (campo.value.length == conjunto1) {
            campo.value = campo.value + separador1;
        }
        if (campo.value.length == conjunto2) {
            campo.value = campo.value + separador2;
        }
        if (campo.value.length == conjunto3) {
            campo.value = campo.value + separador3;
        }
    }

}

//--------------------

/**  
 * Fun��o Principal 
 * @param w - O elemento que ser� aplicado (normalmente this).
 * @param e - O evento para capturar a tecla e cancelar o backspace.
 * @param m - A m�scara a ser aplicada.
 * @param r - Se a m�scara deve ser aplicada da direita para a esquerda. Veja Exemplos.
 * @param a - 
 * @returns null
 *
 * EX.:
 * CPF: onkeyup="maskIt(this,event,'###.###.###-##')"
 * Telefone: onkeyup="maskIt(this,event,'(##)####-####')"
 * Dinheiro: onkeyup="maskIt(this,event,'###.###.###,##',true,{pre:'R$'})"
 * Graus: onkeyup="maskIt(this,event,'###,#',true,{pre:'',pos:'�'})"
 */
function maskIt(w, e, m, r, a) {

    // Cancela se o evento for Backspace
    if (!e)
        var e = window.event
    if (e.keyCode)
        code = e.keyCode;
    else if (e.which)
        code = e.which;

    // Vari�veis da fun��o
    var txt = (!r) ? w.value.replace(/[^\d]+/gi, '') : w.value.replace(/[^\d]+/gi, '').reverse();
    var mask = (!r) ? m : m.reverse();
    var pre = (a) ? a.pre : "";
    var pos = (a) ? a.pos : "";
    var ret = "";

    if (code == 9 || code == 8 || txt.length == mask.replace(/[^#]+/g, '').length)
        return false;

    // Loop na m�scara para aplicar os caracteres
    for (var x = 0, y = 0, z = mask.length; x < z && y < txt.length; ) {
        if (mask.charAt(x) != '#') {
            ret += mask.charAt(x);
            x++;
        } else {
            ret += txt.charAt(y);
            y++;
            x++;
        }
    }

    // Retorno da fun��o
    ret = (!r) ? ret : ret.reverse()
    w.value = pre + ret + pos;
}

// Novo m�todo para o objeto 'String'
String.prototype.reverse = function() {
    return this.split('').reverse().join('');
}
//--------------------


//valida o CPF digitado
function ValidarCPF(Objcpf) {
    var CPF = Objcpf.value; // Recebe o valor digitado no campo
    exp = /\.|\-/g
    CPF = CPF.toString().replace(exp, "");

    // Aqui come�a a checagem do CPF
    var POSICAO, I, SOMA, DV, DV_INFORMADO;
    var DIGITO = new Array(10);
    DV_INFORMADO = CPF.substr(9, 2); // Retira os dois �ltimos d�gitos do n�mero informado

    // Desemembra o n�mero do CPF na array DIGITO
    for (I = 0; I <= 8; I++) {
        DIGITO[I] = CPF.substr(I, 1);
    }

    // Calcula o valor do 10� d�gito da verifica��o
    POSICAO = 10;
    SOMA = 0;
    for (I = 0; I <= 8; I++) {
        SOMA = SOMA + DIGITO[I] * POSICAO;
        POSICAO = POSICAO - 1;
    }
    DIGITO[9] = SOMA % 11;
    if (DIGITO[9] < 2) {
        DIGITO[9] = 0;
    }
    else {
        DIGITO[9] = 11 - DIGITO[9];
    }

    // Calcula o valor do 11� d�gito da verifica��o
    POSICAO = 11;
    SOMA = 0;
    for (I = 0; I <= 9; I++) {
        SOMA = SOMA + DIGITO[I] * POSICAO;
        POSICAO = POSICAO - 1;
    }
    DIGITO[10] = SOMA % 11;
    if (DIGITO[10] < 2) {
        DIGITO[10] = 0;
    }
    else {
        DIGITO[10] = 11 - DIGITO[10];
    }

    // Verifica se os valores dos d�gitos verificadores conferem
    DV = DIGITO[9] * 10 + DIGITO[10];
    if (DV != DV_INFORMADO && CPF != "") {
        alert('CPF inv�lido');
        Objcpf.value = '';
        Objcpf.CPF.focus();
        return false;
    }
}

//valida telefone
function ValidaTelefone(ObjTel) {
    tel = ObjTel.value;
    exp = /\(\d{2}\)\d{4}\-\d{4}/
    if (!exp.test(tel) && tel != "") {
        alert("Informar o DDD! \n\nFavor inserir o telefone no formato: (XX)XXXX-XXXX");
        ObjTel.value = '';
        ObjTel.focus();
    }
}

//valida CEP
function ValidaCep(cep) {
    exp = /\d{2}\.\d{3}\-\d{3}/
    if (!exp.test(cep.value))
        alert('Numero de Cep Invalido!');
}

//transforma string para mai�scula
function Uppercase(Object) {
    var palavra = Object.value;
    Object.value = palavra.toUpperCase();
}

//Valida data
function validaData(campo, valor) {
    var date = valor;
    var ardt = new Array;
    var ExpReg = new RegExp("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/[12][0-9]{3}");
    ardt = date.split("/");
    erro = false;
    if (date.search(ExpReg) == -1) {
        erro = true;
    }
    else if (((ardt[1] == 4) || (ardt[1] == 6) || (ardt[1] == 9) || (ardt[1] == 11)) && (ardt[0] > 30))
        erro = true;
    else if (ardt[1] == 2) {
        if ((ardt[0] > 28) && ((ardt[2] % 4) != 0))
            erro = true;
        if ((ardt[0] > 29) && ((ardt[2] % 4) == 0))
            erro = true;
    }
    if (erro && valor != "") {
        alert("\"" + valor + "\" n�o � uma data v�lida! \n\nFavor inserir a data no formato: dd/mm/aaaa");
        campo.focus();
        campo.value = "";
        return false;
    }
    return true;
}

//Valida HORA
function VerificaHora(campo) {
    hrs = (campo.value.substring(0, 2));
    min = (campo.value.substring(3, 5));
    estado = "";
    if (((hrs < 00) || (hrs > 23) || (min < 00) || (min > 59)) && campo.value != "") {
        alert("Hora invalida!");
        campo.focus();
        campo.value = "";
        return false;
    }

    return true;
}


//------------------------------------------------------
//Fun��es para verificar o email
function checkMail(Object) {
    var email = Object.value;
    if (!is_email(email) && email != "") {
        alert("Email inválido");
        Object.value = "";
        Object.focus();
    }
}

function is_email(email) {
    er = /^[a-zA-Z0-9][a-zA-Z0-9\._-]+@([a-zA-Z0-9\._-]+\.)[a-zA-Z-0-9]{2}/;

    if (er.exec(email)) {
        return true;
    } else {
        return false;
    }
}
//----------------------------------------------------------




function notSpecialAndNumbers(campo) {
    var x = campo;
    c1 = x.value;

    var charpos = c1.search("[^A-Za-z- ]");
    if (c1.length > 0 && charpos >= 0)
    {
        alert("Você não pode digitar acentos, caracteres especiais e números neste Campo.");
        campo.value = c1.substring(charpos, "");
        return false;
    }
    return true;
}
// como usar:
// onkeyup="notEspecial(this);"
// -------------------------------------------------------------------------


function reais(obj, event) {

    var whichCode = (window.Event) ? event.which : event.keyCode;
    /*
     Executa a formata��o ap�s o backspace nos navegadores !document.all
     */
    if (whichCode == 8 && !documentall) {
        /*
         Previne a a��o padr�o nos navegadores
         */
        if (event.preventDefault) { //standart browsers
            event.preventDefault();
        } else { // internet explorer
            event.returnValue = false;
        }
        var valor = obj.value;
        var x = valor.substring(0, valor.length - 1);
        obj.value = demaskvalue(x, true).formatCurrency();
        return false;
    }
    /*
     Executa o Formata Reais e faz o format currency novamente ap�s o backspace
     */
    FormataReais(obj, '.', ',', event);
}

// Modo de usar: onkeypress="reais(this,event)" onkeydown="backspace(this,event)"

//------------------------------------------------------------------------------

function backspace(obj, event) {
    /*
     Essa fun��o basicamente altera o  backspace nos input com m�scara reais para os navegadores IE e opera.
     O IE n�o detecta o keycode 8 no evento keypress, por isso, tratamos no keydown.
     Como o opera suporta o infame document.all, tratamos dele na mesma parte do c�digo.
     */

    var whichCode = (window.Event) ? event.which : event.keyCode;
    if (whichCode == 8 && documentall) {
        var valor = obj.value;
        var x = valor.substring(0, valor.length - 1);
        var y = demaskvalue(x, true).formatCurrency();

        obj.value = ""; //necess�rio para o opera
        obj.value += y;

        if (event.preventDefault) { //standart browsers
            event.preventDefault();
        } else { // internet explorer
            event.returnValue = false;
        }
        return false;

    }// end if
}// end backspace

function FormataReais(fld, milSep, decSep, e) {
    var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;

    //if (whichCode == 8 ) return true; //backspace - estamos tratando disso em outra fun��o no keydown
    if (whichCode == 0)
        return true;
    if (whichCode == 9)
        return true; //tecla tab
    if (whichCode == 13)
        return true; //tecla enter
    if (whichCode == 16)
        return true; //shift internet explorer
    if (whichCode == 17)
        return true; //control no internet explorer
    if (whichCode == 27)
        return true; //tecla esc
    if (whichCode == 34)
        return true; //tecla end
    if (whichCode == 35)
        return true;//tecla end
    if (whichCode == 36)
        return true; //tecla home

    /*
     O trecho abaixo previne a a��o padr�o nos navegadores. N�o estamos inserindo o caractere normalmente, mas via script
     */

    if (e.preventDefault) { //standart browsers
        e.preventDefault()
    } else { // internet explorer
        e.returnValue = false
    }

    var key = String.fromCharCode(whichCode);  // Valor para o c�digo da Chave
    if (strCheck.indexOf(key) == -1)
        return false;  // Chave inv�lida

    /*
     Concatenamos ao value o keycode de key, se esse for um n�mero
     */
    fld.value += key;

    var len = fld.value.length;
    var bodeaux = demaskvalue(fld.value, true).formatCurrency();
    fld.value = bodeaux;

    /*
     Essa parte da fun��o t�o somente move o cursor para o final no opera. Atualmente n�o existe como mov�-lo no konqueror.
     */
    if (fld.createTextRange) {
        var range = fld.createTextRange();
        range.collapse(false);
        range.select();
    }
    else if (fld.setSelectionRange) {
        fld.focus();
        var length = fld.value.length;
        fld.setSelectionRange(length, length);
    }
    return false;

}

// Modo de usar: onkeypress="reais(this,event)" onkeydown="backspace(this,event)"