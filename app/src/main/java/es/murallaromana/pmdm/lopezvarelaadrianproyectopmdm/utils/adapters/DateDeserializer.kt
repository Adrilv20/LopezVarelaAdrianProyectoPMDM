package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateDeserializer
import java.lang.reflect.Type
import java.time.LocalDate

/* NOT IN USE. Left here for learning purposes. */
class DateDeserializer : JsonDeserializer<LocalDate> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        return dateDeserializer(json!!.asJsonPrimitive.asString)
    }
}