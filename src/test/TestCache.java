package test;

import java.io.IOException;
import java.io.InputStream;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;





public class TestCache {
    public static void main(String[] args) throws InterruptedException {
        InputStream is=null;
        CacheManager manager=null;
        try {
            is = TestCache.class.getResourceAsStream("/ehcache.xml");
            manager = CacheManager.newInstance(is);
        } catch (CacheException e1) {
            try {
                if(is!=null){
                    is.close();
                    is=null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            e1.printStackTrace();
        }

        Cache cache = manager.getCache("myCache");

        Element element = new Element("client3" + System.currentTimeMillis(), "client3");
        cache.put(element);
        int i=0;
        while (true)
        {
            Element element2 = new Element("client-3-"+i,i);
            cache.put(element2);
            Thread.sleep(3000);
            System.out.println("\n");
            for (Object key : cache.getKeys())
            {
                System.out.println(key + ":" + cache.get(key).getObjectValue());
            }
            i++;
        }
    }
}
