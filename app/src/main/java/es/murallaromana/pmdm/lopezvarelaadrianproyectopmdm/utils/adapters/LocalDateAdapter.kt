package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateDeserializer
import es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.utils.dateSerializer
import java.time.LocalDate

/*
* Used to customize the date format used when serializing and deserializing films.
* See DateDeserializer for alternative (different API, would need an aditional class for serializing)
*/
class LocalDateAdapter: TypeAdapter<LocalDate>() {
    override fun write(out: JsonWriter?, value: LocalDate?) {
        out?.value(dateSerializer(value!!))
    }

    override fun read(`in`: JsonReader?): LocalDate {
        return dateDeserializer(`in`!!.nextString())
    }

}