package com.zebra.context;

public final class ContextHolder {

    /**
     * The thread local variable to hold the thread specific state.
     */
    private static final ThreadLocal<Context> contextThreadLocal =
            new InheritableThreadLocal<Context>();
    
    private static String protocol;

    /**
     * Instantiation not allowed.
     */
    private ContextHolder() {
    }

    /**
     * Sets the Context into the inner InheritableThreadLocal instance
     * 
     * @param context
     *            the UserContext to set.
     */
    public static void setContext(final Context context) {
        contextThreadLocal.set(context);
    }

    /**
     * Returns an instance of Context from the inner InheritableThreadLocal
     * instance
     * 
     * @return Context the <code>Context</code> held by the current Thread or
     *         null if no context has been set.
     */
    public static Context getContext() {
        return (Context) contextThreadLocal.get();
    }

    /**
     * Removes the Context held by the current Thread.
     */
    public static void removeContext() {
        contextThreadLocal.set(null);
    }

    /**
     * Return whether the context exists.
     */
    public static boolean hasContext() {
        return (contextThreadLocal.get() != null);
    }
    
    public static String getProtocol() {
        return protocol;
    }

    public static void setProtocol(String aProtocol) {
        protocol = aProtocol;
    }    
}
