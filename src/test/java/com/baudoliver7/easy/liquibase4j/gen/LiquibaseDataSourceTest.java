/*
 * MIT License
 *
 * Copyright (c) 2022 Olivier B. OURA
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.baudoliver7.easy.liquibase4j.gen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import org.h2.jdbcx.JdbcDataSource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link LiquibaseDataSource}.
 *
 * @since 0.1
 */
final class LiquibaseDataSourceTest {

    @Test
    void generatesSimple() throws SQLException {
        final JdbcDataSource src = new JdbcDataSource();
        src.setUrl("jdbc:h2:~/test");
        src.setUser("sa");
        src.setPassword("");
        new LiquibaseDataSource(
            src,
            "liquibase/db.changelog-master.xml"
        );
        try (Connection conn = src.getConnection()) {
            MatcherAssert.assertThat(
                LiquibaseDataSourceTest.tableExists(conn, "ad_person"),
                Matchers.is(true)
            );
        }
    }

    /**
     * Checks if a table exists.
     * @param connection Connection
     * @param tname Table name
     * @return Exists or not
     * @throws SQLException If fails
     */
    private static boolean tableExists(
        final Connection connection, final String tname) throws SQLException {
        final DatabaseMetaData meta = connection.getMetaData();
        try (ResultSet rset = meta.getTables(
            null, null, tname.toUpperCase(Locale.ENGLISH), new String[] {"TABLE"}
        )) {
            return rset.next();
        }
    }
}
