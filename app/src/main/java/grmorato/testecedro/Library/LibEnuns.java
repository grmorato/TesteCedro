package grmorato.testecedro.Library;

/**
 * Created by grmorato on 09/02/2018.
 */

//Classe para conter todos os enuns da aplicação
public class LibEnuns
{
    //Enum com os tipos de dados suportados no sqlite para realizar o cast ao fazer as operações de banco de dados
    //deixando tudo mais generico
    //Só foi adicionado alguns campos a medida que for vendo mais campos necessários ir incluindo aqui
    public enum DataBaseType
    {
        Integer,
        String,
        ByteArray,
        Long,
        Float,
        Byte
    }

}
