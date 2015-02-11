//valida deducao gripma
function isDeducaoValido()
{
    var valor = document.getElementById('form:valorGripmaDeducao').value;
    var tipo_deducao = document.getElementById('form:gripmaDeducoes').value;

    if(valor!="0,00"&&valor!=""&&tipo_deducao!="0")
    {
        document.getElementById('form:btn_add_deducao').disabled=false;

    //        document.getElementById('gripmaDeducaoButtonPanel').style.display = "block";
    }else{
        document.getElementById('form:btn_add_deducao').disabled=true;

    //        document.getElementById('gripmaDeducaoButtonPanel').style.display = "none";
    }
}

function ativaAdicionarSolicitacoes() {
    document.getElementById('formLinha1').style.display = 'block';
}
//-------------------------------------------------------------------------
 

// Pesquisa de Formularios do Cadastro Funcional

function searchPlus(cp, option, clean) {
    if (option == "open") {
        document.getElementById(cp).style.display = "block";
    }
    if (option == "close") {
        document.getElementById(cp).style.display = "none";
        document.getElementById(clean).value = " ";
    }
}

// ---------------------------------------------------------------------------

// Inicial Bot

function onBot(div) {
    document.getElementById(div).style.color = '#9CE2FA';
}

function downBot(div) {
    document.getElementById(div).style.color = '#FFF';
}

// -------------------------------------------------------------------------
// funcao uppercase

function upperCase(event) {
    var keynum =  (event = event || window.event).keyCode ? event.keyCode : event.which;

    if ((keynum >= 97 && keynum <= 122) || (keynum >= 224 && keynum <= 255)) {
        // converte de acordo com o valor decimal da tecla na tabela ascii
        keynum = keynum - 32;

        // IE
        if (window.event){

            window.event.keyCode = keynum;
        }
        // firefox e outros que usam o Gecko
        else if (event.which) {
            var newEvent = document.createEvent("KeyEvents");
            newEvent.initKeyEvent("keypress", true, true, document.defaultView,
                event.ctrlKey, event.altKey, event.shiftKey,
                event.metaKey, 0, keynum);
            event.preventDefault();
            event.target.dispatchEvent(newEvent);
        }
    }

    return true;
}

// como usar:
//   <input name="teste" type="text" onkeypress="upperCase(event);" >
//--------------------------------------------------------------------------
//
//funcao mascara num�rica(funciona pra iexplorer e pra firefox) muito bom!

function pontoPvirgula(Campo,entrada){
    var pVirgula = function(str){
        return (String(str)).replace(/\./g,'').replace(/,/g,'.');
    }
    valorTotal = pVirgula(document.form1.ValorTotalCompraCartao.value);
    entrada = valorTotal * 1;
    valorCompraPagar = valorTotal - entrada;
    Campo.value = pVirgula(valorCompraPagar.toFixed(2));
}

function FormataCampo(Campo,teclapres,mascara){
    //pegando o tamanho do texto da caixa de texto com delay de -1 no event
    //ou seja o caractere que foi digitado n�o ser� contado.
    strtext = Campo.value
    tamtext = strtext.length
    //pegando o tamanho da mascara
    tammask = mascara.length
    //criando um array para guardar cada caractere da m�scara
    arrmask = new Array(tammask)
    //jogando os caracteres para o vetor
    for (var i = 0 ; i < tammask; i++){
        arrmask[i] = mascara.slice(i,i+1)
    }
    //alert (teclapres.keyCode)
    //come�ando o trabalho sujo
    if (((((arrmask[tamtext] == "#") || (arrmask[tamtext] == "9"))) || (((arrmask[tamtext+1] != "#") || (arrmask[tamtext+1] != "9"))))){
        if ((teclapres.keyCode >= 37 && teclapres.keyCode <= 40)||(teclapres.keyCode >= 48 && teclapres.keyCode <= 57)||(teclapres.keyCode >= 96 && teclapres.keyCode <= 105)||(teclapres.keyCode == 8)||(teclapres.keyCode == 9) ||(teclapres.keyCode == 46) ||(teclapres.keyCode == 13)){
            Organiza_Casa(Campo,arrmask[tamtext],teclapres.keyCode,strtext)
        }
        else{
            Detona_Event(Campo,strtext)
        }
    }
    else{//Aqui funcionaria a mascara para n�meros mas eu ainda n�o implementei
        if ((arrmask[tamtext] == "A")) {
            charupper = event.valueOf()
            //charupper = charupper.toUpperCase()
            Detona_Event(Campo,strtext)
            masktext = strtext + charupper
            Campo.value = masktext
        }
    }
}
function Organiza_Casa(Campo,arrpos,teclapres_key,strtext){
    if (((arrpos == "/") || (arrpos == ".") || (arrpos == "(") || (arrpos == ")") || (arrpos == ",") || (arrpos == ":") || (arrpos == " ") || (arrpos == "-")) && !(teclapres_key == 8)){
        separador = arrpos
        masktext = strtext + separador
        Campo.value = masktext
    }
}
function Detona_Event(Campo,strtext){
    event.returnValue = false
    if (strtext != "") {
        Campo.value = strtext
    }
}

//como usar:
// onkeydown="FormataCampo(this,event,'###.###.###-##')"
//--------------------------------------------------------------------------
//da uma mensagem de erro ao digitar caractere especial e pontua��o

function notEspPont(campo)
{
    var x = campo;
    c1 = x.value;

    var charpos = c1.search("[^A-Za-z-0-9- ]");
    if(c1.length > 0 &&  charpos >= 0)
    {
        alert("Voc� n�o pode digitar [Car�cteres especiais] e [Pontos] neste Campo.");
        campo.value =  c1.substring(charpos, "");
        return false;
    }
}

// como usar:
// onkeyup="notEspPont(this);"
// -------------------------------------------------------------------------
//da uma mensagem de erro ao digitar caractere especial

function notEspecial(campo){
    var x = campo;
    c1 = x.value;

    var charpos = c1.search("[^A-Za-z-0-9-., ]");
    if(c1.length > 0 &&  charpos >= 0)
    {
        alert("Você não pode digitar caracteres especiais neste Campo.");
        campo.value =  c1.substring(charpos, "");
        return false;
    }
    return true;
}

//to UPPERCASE
function Maiuscula(Object) {
    var palavra = Object.value;
    Object.value = palavra.toUpperCase();
}
// como usar:
// onkeyup="notEspecial(this);"
// -------------------------------------------------------------------------

// Focus no primeiro txtfield do primeiro form
function focusOnFirstInput() {
    var form = document.forms[1];

    for (var j = 0; j < form.length; j++) {
        var input = form[j];
        if (input.type != "hidden"
            && input.type != "button"
            && input.type != "submit") {

            if (!input.disabled) {
                input.focus();
                return;
            }
        }
    }
}

//--------------------------------------------------------------------------

// s� permite digitar numeros na textfield

function apenasNum(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode;
    return (charCode>=48 && charCode<=57 || charCode<20);
}

// como usar:
// onkeypress="return apenasNum(event);"


//--------------------------------------------------------------------------

// s� permite digitar numeros na textfield

function testPassword()
{
    var campo1 = document.getElementById('password').value;
    var campo2 = document.getElementById('passwordTest').value;
    if(campo1 == campo2) {
        return true;
    } else {
        alert("As senhas n�o conferem");
        return false;
    }
}

// como usar:
// onkeypress="return apenasNum(event);"

// ---------------------------------------------------------------------------------------

function showTipoLotacao(x) {

    if(x == document.getElementById("form:tipo:0").id){
        document.getElementById("lotacaoSinteticaPanel").style.display="none";
        document.getElementById("lotacaoSinteticaPanel").disabled=true;
    } else {
        document.getElementById("lotacaoSinteticaPanel").style.display="block";
        document.getElementById("lotacaoSinteticaPanel").disabled=false;
    }
}

function setFocus()
{
    var f = null;
    if (document.getElementById)
    {
        f = document.getElementById("mail");
    }
    else if (window.mail)
    {
        f = window.mail;
    }
    if (f)
    {
        if (f.login_user && (f.login_user.value == null || f.login_user.value == ""))
        {
            f.login_user.focus();
        }
        else if (f.secretkey01)
        {
            f.secretkey01.focus();
        }
    }
}

function OnLoad() {
    setFocus();
}

//--------------------------------------------------------------------------

// Usado pra setar foco

function setFoco(x) {
    document.getElementById(x).focus();
}
//--------------------------------------------------------------------------
// ativa painel da impressoras
function ativaFormPrinter() {
    document.getElementById('formPrinterPanel').style.display = 'block';

    if (document.all) { // IE
        document.getElementById('divIE').style.display = 'block';
    } else {
        document.getElementById('divFX').style.display = 'block';
    }
}
//--------------------------------------------------------------------------
//remove o painel das impressoras
function desativaFormPrinter() {
    if (document.all) { // IE
        document.getElementById('formPrinterPanel').style.display = 'none';
        document.getElementById('divIE').style.display = 'none';
    } else {
        document.getElementById('formPrinterPanel').style.display = 'none';
        document.getElementById('divFX').style.display = 'none';
    }
}

// formata estilo moeda (real)
function formatamoney(c) {
    var t = this;
    if(c == undefined) c = 2;
    var p, d = (t=t.split("."))[1].substr(0, c);
    for(p = (t=t[0]).length; (p-=3) >= 1;) {
        t = t.substr(0,p) + "." + t.substr(p);
    }
    return t+","+d+Array(c+1-d.length).join(0);
}

String.prototype.formatCurrency=formatamoney

function demaskvalue(valor, currency){
    /*
* Se currency � false, retorna o valor sem apenas com os n�meros. Se � true, os dois �ltimos caracteres s�o considerados as
* casas decimais
*/
    var val2 = '';
    var strCheck = '0123456789';
    var len = valor.length;
    if (len== 0){
        return 0.00;
    }

    if (currency ==true){
        /* Elimina os zeros � esquerda
		* a vari�vel  <i> passa a ser a localiza��o do primeiro caractere ap�s os zeros e
		* val2 cont�m os caracteres (descontando os zeros � esquerda)
		*/

        for(var i = 0; i < len; i++)
            if ((valor.charAt(i) != '0') && (valor.charAt(i) != ',')) break;

        for(; i < len; i++){
            if (strCheck.indexOf(valor.charAt(i))!=-1) val2+= valor.charAt(i);
        }

        if(val2.length==0) return "0.00";
        if (val2.length==1)return "0.0" + val2;
        if (val2.length==2)return "0." + val2;

        var parte1 = val2.substring(0,val2.length-2);
        var parte2 = val2.substring(val2.length-2);
        var returnvalue = parte1 + "." + parte2;
        return returnvalue;

    }
    else{
        /* currency � false: retornamos os valores COM os zeros � esquerda,
			* sem considerar os �ltimos 2 algarismos como casas decimais
			*/
        val3 ="";
        for(var k=0; k < len; k++){
            if (strCheck.indexOf(valor.charAt(k))!=-1) val3+= valor.charAt(k);
        }
        return val3;
    }
}

function reais(obj,event){

    var whichCode = (window.Event) ? event.which : event.keyCode;
    /*
Executa a formata��o ap�s o backspace nos navegadores !document.all
*/
    if (whichCode == 8 && !documentall) {
        /*
Previne a a��o padr�o nos navegadores
*/
        if (event.preventDefault){ //standart browsers
            event.preventDefault();
        }else{ // internet explorer
            event.returnValue = false;
        }
        var valor = obj.value;
        var x = valor.substring(0,valor.length-1);
        obj.value= demaskvalue(x,true).formatCurrency();
        return false;
    }
    /*
Executa o Formata Reais e faz o format currency novamente ap�s o backspace
*/
    FormataReais(obj,'.',',',event);
}
// end reais

function backspace(obj,event){
    /*
Essa fun��o basicamente altera o  backspace nos input com m�scara reais para os navegadores IE e opera.
O IE n�o detecta o keycode 8 no evento keypress, por isso, tratamos no keydown.
Como o opera suporta o infame document.all, tratamos dele na mesma parte do c�digo.
*/

    var whichCode = (window.Event) ? event.which : event.keyCode;
    if (whichCode == 8 && documentall) {
        var valor = obj.value;
        var x = valor.substring(0,valor.length-1);
        var y = demaskvalue(x,true).formatCurrency();

        obj.value =""; //necess�rio para o opera
        obj.value += y;

        if (event.preventDefault){ //standart browsers
            event.preventDefault();
        }else{ // internet explorer
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
    if (whichCode == 0 ) return true;
    if (whichCode == 9 ) return true; //tecla tab
    if (whichCode == 13) return true; //tecla enter
    if (whichCode == 16) return true; //shift internet explorer
    if (whichCode == 17) return true; //control no internet explorer
    if (whichCode == 27 ) return true; //tecla esc
    if (whichCode == 34 ) return true; //tecla end
    if (whichCode == 35 ) return true;//tecla end
    if (whichCode == 36 ) return true; //tecla home

    /*
O trecho abaixo previne a a��o padr�o nos navegadores. N�o estamos inserindo o caractere normalmente, mas via script
*/

    if (e.preventDefault){ //standart browsers
        e.preventDefault()
    }else{ // internet explorer
        e.returnValue = false
    }

    var key = String.fromCharCode(whichCode);  // Valor para o c�digo da Chave
    if (strCheck.indexOf(key) == -1) return false;  // Chave inv�lida

    /*
Concatenamos ao value o keycode de key, se esse for um n�mero
*/
    fld.value += key;

    var len = fld.value.length;
    var bodeaux = demaskvalue(fld.value,true).formatCurrency();
    fld.value=bodeaux;

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

//-----------------------------------------------------------------------------

//funcao mascara (funciona pra iexplorer e pra firefox) muito bom!

function FormataCampo(Campo,teclapres,mascara){
    //pegando o tamanho do texto da caixa de texto com delay de -1 no event
    //ou seja o caractere que foi digitado n�o ser� contado.
    strtext = Campo.value
    tamtext = strtext.length
    //pegando o tamanho da mascara
    tammask = mascara.length
    //criando um array para guardar cada caractere da m�scara
    arrmask = new Array(tammask)
    //jogando os caracteres para o vetor
    for (var i = 0 ; i < tammask; i++){
        arrmask[i] = mascara.slice(i,i+1)
    }
    //alert (teclapres.keyCode)
    //come�ando o trabalho sujo
    if (((((arrmask[tamtext] == "#") || (arrmask[tamtext] == "9"))) || (((arrmask[tamtext+1] != "#") || (arrmask[tamtext+1] != "9"))))){
        if ((teclapres.keyCode >= 37 && teclapres.keyCode <= 40)||(teclapres.keyCode >= 48 && teclapres.keyCode <= 57)||(teclapres.keyCode >= 96 && teclapres.keyCode <= 105)||(teclapres.keyCode == 8)||(teclapres.keyCode == 9) ||(teclapres.keyCode == 46) ||(teclapres.keyCode == 13)){
            Organiza_Casa(Campo,arrmask[tamtext],teclapres.keyCode,strtext)
        }
        else{
            Detona_Event(Campo,strtext)
        }
    }
    else{//Aqui funcionaria a mascara para n�meros mas eu ainda n�o implementei
        if ((arrmask[tamtext] == "A")) {
            charupper = event.valueOf()
            //charupper = charupper.toUpperCase()
            Detona_Event(Campo,strtext)
            masktext = strtext + charupper
            Campo.value = masktext
        }
    }
}

function Organiza_Casa(Campo,arrpos,teclapres_key,strtext){
    if (((arrpos == "/") || (arrpos == ".") || (arrpos == "(") || (arrpos == ")") || (arrpos == ",") || (arrpos == ":") || (arrpos == " ") || (arrpos == "-")) && !(teclapres_key == 8)){
        separador = arrpos
        masktext = strtext + separador
        Campo.value = masktext
    }
}

function Detona_Event(Campo,strtext){
    event.returnValue = false
    if (strtext != "") {
        Campo.value = strtext
    }
}

// Modo de usar: onkeydown="FormataCampo(this,event,'###.###.###-##')"

// --------------------------------------------------------------------------------------------------------------------------

// apenas testa o numero do pis_pasep!

function ChecaPIS(pis) {

    var part1 = pis.substr(0,3);
    var part2 = pis.substr(4,5);
    var part3 = pis.substr(10,2);
    var part4 = pis.substr(13,1);

    total=0;
    resto=0;
    numPIS=0;
    strResto="";

    numPIS = part1 + part2 + part3 + part4;

    if (numPIS=="" || numPIS==null)
    {
        alert("Pis/Pasep Inv�lido")
        return false;
    }

    for(i=0;i<=9;i++)
    {
        resultado = (numPIS.slice(i,i+1))*(ftap.slice(i,i+1));
        total=total+resultado;
    }

    resto = (total % 11)

    if (resto != 0)
    {
        resto=11-resto;
    }

    if (resto==10 || resto==11)
    {
        strResto=resto+"";
        resto = strResto.slice(1,2);
    }

    if (resto!=(numPIS.slice(10,11)))
    {

        alert("Pis/Pasep Inv�lido")
        return false;
    }

    return true;
}

//-------------------------------------------------------------------------------------------------------------------------
//
// apenas testa o numero do cpf!

function validacpf(s)
{
    var i;
    var part1 = s.substr(0,3);
    var part2 = s.substr(4,3);
    var part3 = s.substr(8,3);
    var c = part1 + part2 + part3;
    var dv = s.substr(12,2);
    var d1 = 0;
    for (i = 0; i < 9; i++)
    {
        d1 += c.charAt(i)*(10-i);
    }
    if (d1 == 0)
    {
        alert("CPF Invalido")
        return false;
    }
    d1 = 11 - (d1 % 11);
    if (d1 > 9) d1 = 0;
    if (dv.charAt(0) != d1)
    {
        alert("CPF Invalido")
        return false;
    }
    d1 *= 2;
    for (i = 0; i < 9; i++)
    {
        d1 += c.charAt(i)*(11-i);
    }
    d1 = 11 - (d1 % 11);
    if (d1 > 9) d1 = 0;
    if (dv.charAt(1) != d1)
    {
        alert("CPF Invalido")
        return false;
    }
    return true;
}

//-------------------------------------------------------------------------------------------------------------------------

function maxDraggable() {
    var draggable = 'draggable';
    var draggableContent = 'draggableContent';
    document.getElementById(draggable).style.height = '200px';
    document.getElementById(draggableContent).style.display = 'block';
}

function minDraggable() {
    var draggable = 'draggable';
    var draggableContent = 'draggableContent';
    document.getElementById(draggable).style.height = '16px';
    document.getElementById(draggableContent).style.display = 'none';
}

function closeDraggable() {
    var draggable = 'draggable';
    document.getElementById(draggable).style.display = 'none';
}

//-------------------------------------------------------------------------------------------------------------------------
//USADO NO EMPENHO PATRIMONIO
function ativaAdicionarItens() {
    document.getElementById('formAdicionaItemPanel').style.display = 'block';

    if (document.all) { // IE
        document.getElementById('divIE').style.display = 'block';
    } else {
        document.getElementById('divFX').style.display = 'block';
    }
}
//--------------------------------------------------------------------------

//remove o painel dos cargos
function desativaAdicionarItens() {
    if (document.all) { // IE
        document.getElementById('formAdicionaItemPanel').style.display = 'none';
        document.getElementById('divIE').style.display = 'none';
    } else {
        document.getElementById('formAdicionaLotacaoPanel').style.display = 'none';
        document.getElementById('divFX').style.display = 'none';
    }
}