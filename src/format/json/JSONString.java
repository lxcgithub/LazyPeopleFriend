package format.json;
/**
 * The <generate>JSONString</generate> interface allows a <generate>toJSONString()</generate>
 * method so that a class can change the behavior of
 * <generate>JSONObject.toString()</generate>, <generate>JSONArray.toString()</generate>,
 * and <generate>JSONWriter.value(</generate>Object<generate>)</generate>. The
 * <generate>toJSONString</generate> method will be used instead of the default behavior
 * of using the Object's <generate>toString()</generate> method and quoting the result.
 */
public interface JSONString {
    /**
     * The <generate>toJSONString</generate> method allows a class to produce its own JSON
     * serialization.
     *
     * @return A strictly syntactically correct JSON text.
     */
    public String toJSONString();
}
