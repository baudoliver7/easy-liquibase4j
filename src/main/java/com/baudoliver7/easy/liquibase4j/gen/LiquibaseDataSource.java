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

import com.baudoliver7.jdbc.toolset.wrapper.DataSourceWrap;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Data source that upgrades automatically database with liquibase.
 *
 * @since 0.1
 */
public final class LiquibaseDataSource extends DataSourceWrap {

    /**
     * Ctor.
     * <p>We run immediately the script.</p>
     * @param origin Origin
     * @param chgpath Change log path relative to resource accessor
     * @throws SQLException If fails
     */
    public LiquibaseDataSource(
        final DataSource origin, final String chgpath) throws SQLException {
        super(LiquibaseDataSource.upgrade(origin, chgpath));
    }

    /**
     * Upgrades database.
     * @param src Data source
     * @param chgpath Change log path
     * @return Data source upgraded
     * @throws SQLException If fails
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private static DataSource upgrade(
        final DataSource src, final String chgpath) throws SQLException {
        Connection connection = null;
        try {
            connection = src.getConnection();
            connection.setAutoCommit(false);
            new LiquibaseConnection(connection, chgpath);
            connection.commit();
            return src;
        } catch (final SQLException exe) {
            if (connection != null) {
                connection.rollback();
            }
            throw exe;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
