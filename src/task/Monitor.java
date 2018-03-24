
package task;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import json.JSON;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * used to monitor a Task
 * 
 *
 */
public class Monitor {

  static Log                       log   = LogFactory.getLog(Monitor.class);

  static private AtomicLong        seq   = new AtomicLong();

  static private Map<Long, Object> cache = new HashMap<Long, Object>();

  public synchronized static void finished(Task t) {
    for (long tid : cache.keySet().toArray(new Long[cache.size()])) {
      if (t == cache.get(tid)) {
        JSON jo = get(tid);
        cache.put(tid, jo);
        return;
      }
    }
  }

  /**
   * Start.
   *
   * @param t
   *          the t
   * @param ms
   *          the ms
   * @return the long
   */
  public static long start(Task t, long ms) {
    long tid = seq.incrementAndGet();
    cache.put(tid, t);
    t.schedule(ms);
    return tid;
  }

  /**
   * Stop.
   *
   * @param tid
   *          the tid
   */
  public static void stop(long tid) {
    Object t = cache.remove(tid);
    if (t != null && t instanceof Task) {
      ((Task) t).stop(true);
    }
  }

  /**
   * Gets the.
   *
   * @param tid
   *          the tid
   * @return the json
   */
  public synchronized static JSON get(long tid) {
    Object t = cache.get(tid);
    if (t != null) {
      if (t instanceof JSON) {
        cache.remove(tid);
        return (JSON) t;
      }

      Field[] fs = t.getClass().getDeclaredFields();
      if (fs != null) {
        JSON jo = JSON.create();
        for (Field f : fs) {
          int p = f.getModifiers();
          // log.debug(f.getName() + "=" + p);
          if ((p & (Modifier.PRIVATE | Modifier.STATIC)) == 0) {
            try {
              f.setAccessible(true);
              jo.put(f.getName(), f.get(t));
            } catch (Exception e) {
              log.error(e.getMessage(), e);
            }
          }
        }

        return jo;
      }
    }
    return null;
  }

}
