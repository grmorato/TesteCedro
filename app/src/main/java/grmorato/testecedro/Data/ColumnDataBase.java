package grmorato.testecedro.Data;

import grmorato.testecedro.Library.LibEnuns;

/**
 * Created by grmorato on 09/02/2018.
 */

//Classe responsável por mapear os dados referente os campos das tabelas
//Deixando assim a manipulação do banco mais generica
public class ColumnDataBase
{
    private String columnName;
    private LibEnuns.DataBaseType type;
    private Object value;

    public ColumnDataBase(String columnName, LibEnuns.DataBaseType type, Object value) {
        this.columnName = columnName;
        this.type = type;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public LibEnuns.DataBaseType getType() {
        return type;
    }

    public void setType(LibEnuns.DataBaseType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}


