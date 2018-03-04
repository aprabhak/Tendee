package com.example.fake9.tendee;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void passwordCheckerEmpty() throws Exception {
        RegisterActivity r = new RegisterActivity();
        assertEquals(false,r.passwordAppropriate(""));

    }

    @Test
    public void passwordCheckerShort() throws Exception {
        RegisterActivity r = new RegisterActivity();
        assertEquals(false,r.passwordAppropriate("1111"));

    }
    @Test
    public void passwordCheckerLongPassword() throws Exception {
        RegisterActivity r = new RegisterActivity();
        assertEquals(false,r.passwordAppropriate("14683518761788413748314678381843814683287147387266439267148"));

    }

    @Test
    public void passwordCheckerProper() throws Exception {
        RegisterActivity r = new RegisterActivity();
        assertEquals(true,r.passwordAppropriate("123@#ABC!"));

    }

    @Test
    public void passwordCheckerLessNumbers() throws Exception {
        RegisterActivity r = new RegisterActivity();
        assertEquals(false,r.passwordAppropriate("1AAA@#$%%"));

    }

    @Test
    public void passwordCheckerAlphaNumericLess() throws Exception {
        RegisterActivity r = new RegisterActivity();
        assertEquals(false,r.passwordAppropriate("1111AAAA"));

    }

    @Test
    public void passwordCheckerOnlyAlphaNumeric() throws Exception {
        RegisterActivity r = new RegisterActivity();
        assertEquals(false,r.passwordAppropriate("AAAAAAAAAAbbbbbbbb"));

    }

    @Test
    public void registrationNameCheck() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validName(""));

    }

    @Test
    public void registrationNameOnlyFirstName() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validName("Avinash"));

    }

    @Test
    public void registrationNameValid() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(true,reg.validName("Avinash Prabhakar"));

    }

    @Test
    public void registrationNameShort() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validName("Avi"));

    }

    @Test
    public void registrationNameInvalid() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validName("Altug#$"));

    }

    @Test
    public void registrationNameInvalid2() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validName("Avi#$%&#@$@#$# 478t7578"));

    }

    @Test
    public void registrationNameBadWords() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(true,reg.containsBadWords("Fucker Me"));

    }

    @Test
    public void registrationNameBadWords2() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(true,reg.containsBadWords("Bitch Jenny"));

    }

    @Test
    public void registrationNameBadWords3() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(true,reg.containsBadWords("Sucky Jenny"));

    }

    @Test
    public void registrationNameBadWords4() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(true,reg.containsBadWords("AsS HoLe Jenny"));

    }

    @Test
    public void registrationBadDescription() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validDescription("I would like to meet with Bitch Jenny"));

    }

    @Test
    public void registrationGoodDescription() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(true,reg.validDescription("I would like to meet with Jenny"));

    }

    @Test
    public void registrationBadDescription2() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validDescription("I would like to meet with Fuck*ng Jenny"));

    }

    @Test
    public void registrationBadDescription3() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validDescription("I would like to m##t wi123th Jenny"));

    }

    @Test
    public void registrationBadDescription4() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validDescription("I"));

    }

    @Test
    public void registrationLongDescription() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validDescription("dakug;rehgjsbgreigbrejqbgbreoqbgre g reqgrqegbrebqghreghreohgireoqhgorhgohrebaighfe;hag;rhegreohgjroe;hgr;hequgor;heuog;hre"));

    }

    @Test
    public void registrationValidDescription() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(true,reg.validDescription("I Want To Meet with Chaolun!!"));

    }

    @Test
    public void registrationInValidDescription() throws Exception {
        RegisterDetailActivity reg = new RegisterDetailActivity();
        assertEquals(false,reg.validDescription("!@#$!@#$%^&*("));

    }

    @Test
    public void registrationValidEmail() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(true,reg.validEmail("altug@gmail.com"));

    }

    @Test
    public void registrationInValidEmail() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validEmail("altug"));

    }

    @Test
    public void registrationInValidEmail2() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validEmail("altug#gmail.com"));

    }

    @Test
    public void registrationInValidEmail3() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validEmail("altug@gmail*com"));

    }

    @Test
    public void registrationValidEmail2() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(true,reg.validEmail("altug@hotmail.com"));

    }

    @Test
    public void registrationInValidEmail4() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validEmail("altug657473656565646546574657367564375646756@hotmail.com"));

    }

    @Test
    public void registrationInValidEmail5() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validEmail("altug@"));

    }

    @Test
    public void registrationInValidEmail6() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validEmail("@"));

    }

    @Test
    public void registrationInValidAddress() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validAddress("Altug"));

    }

    @Test
    public void registrationInValidAddress2() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validAddress("Hiltop Dr No @#"));

    }

    @Test
    public void registrationInValidAddress3() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validAddress("Hiltop No 34"));

    }

    @Test
    public void registrationValidAddress() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(true,reg.validAddress("Hiltop Dr Apt No 34"));

    }

    @Test
    public void registrationInValidAddress5() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validAddress(""));

    }

    @Test
    public void registrationInValidAddress6() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validAddress("248632766325882842547853743"));

    }

    @Test
    public void registrationInValidAddress7() throws Exception {
        RegisterActivity reg = new RegisterActivity();
        assertEquals(false,reg.validAddress("Fucking Dr West Lafayette In"));

    }
}