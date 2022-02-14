package es.murallaromana.pmdm.lopezvarelaadrianproyectopmdm.actionHandlers

/**
 * Interface intended to abstract the handling of click-like actions
 * (onClickListener & onLongClickListener) from the adapter.
 *
 * Any activity implementing this interface will be declaring what a (list) adapter
 * should do when those events trigger.
 */
interface ClickHandler {
    fun onItemClickHandler(position: Int)
    fun onItemLongClickHandler(position: Int) : Boolean
}