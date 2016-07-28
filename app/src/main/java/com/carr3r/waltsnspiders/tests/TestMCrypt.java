package com.carr3r.waltsnspiders.tests;

import com.carr3r.waltsnspiders.MCrypt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by wneto on 06/11/2015.
 */
public class TestMCrypt {

    @Test
    public void descrypt() {

        MCrypt mcrypt = new MCrypt();
        try {
            assertEquals("AEIOU", new String(mcrypt.decrypt("87cde2e58331044278bc4621e63e7c1e")));
            assertEquals("AEIOUAEIOU", new String(mcrypt.decrypt("dbacf6aea1768534ba3e60c9bd1d9f85")));
            assertEquals("AEIOUAEIOUAEIOU", new String(mcrypt.decrypt("bffd03e8c6a32e4bb7df3bc636863110")));
            assertEquals("AEIOUAEIOUAEIOUAEIOU", new String(mcrypt.decrypt("96f2fe867e90775fc28e46c7b63f0f100ed0abb099aa8fd3d55a0b78c8bbcef8")));
            assertEquals("AEIOUAEIOUAEIOUAEIOUAEIOU", new String(mcrypt.decrypt("96f2fe867e90775fc28e46c7b63f0f1066068014838a3f95b9d90d1fc22a92af")));
            assertEquals("AEIOUAEIOUAEIOUAEIOUAEIOUAEIOU", new String(mcrypt.decrypt("96f2fe867e90775fc28e46c7b63f0f1037043392db9acb56f9987f440debd876")));
            assertEquals("AEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOU", new String(mcrypt.decrypt("96f2fe867e90775fc28e46c7b63f0f104240b9a2383b28defd1738b197992f6e43abc508bfece195733cd9e5ae79396f")));
            assertEquals("AEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOU", new String(mcrypt.decrypt("96f2fe867e90775fc28e46c7b63f0f104240b9a2383b28defd1738b197992f6ee24bf74c584778ea73446513134eb2c2")));
            assertEquals("AEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOU", new String(mcrypt.decrypt("96f2fe867e90775fc28e46c7b63f0f104240b9a2383b28defd1738b197992f6edda962c28ac2807e9ead1b9e1341b901")));
            assertEquals("AEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOUAEIOU", new String(mcrypt.decrypt("8632ce062ea0c7cfd27e0637662f7f80a2b06952e85b189e5dd7b80137b9cf0e61209899b13f10f9d320303aa4bc494b1c0c3cbec64c8fc15179907587fff297")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
