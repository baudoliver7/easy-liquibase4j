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

import com.baudoliver7.jdbc.toolset.wrapper.ConnectionWrap;
import java.sql.Connection;
import java.sql.SQLException;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * Connection that upgrades automatically database with liquibase.
 *
 * @since 0.1
 */
public final class LiquibaseConnection extends ConnectionWrap {

    /**
     * Ctor.
     * <p>We run immediately the script.</p>
     * @param origin Origin
     * @param chgpath Change log path relative to resource accessor
     * @throws SQLException If fails
     */
    public LiquibaseConnection(
        final Connection origin, final String chgpath) throws SQLException {
        super(LiquibaseConnection.upgrade(origin, chgpath));
    }

    /**
     * Upgrades database.
     * @param conn Connection
     * @param chgpath Change log path
     * @return Connection upgraded
     * @throws SQLException If fails
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private static Connection upgrade(
        final Connection conn, final String chgpath) throws SQLException {
        try {
            final liquibase.database.Database database =
                DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(
                        new JdbcConnection(conn)
                    );
            try {
                final ClassLoaderResourceAccessor acc = new ClassLoaderResourceAccessor();
                new Liquibase(chgpath, acc, database).update("");
                acc.close();
                return conn;
                // @checkstyle MethodBodyCommentsCheck (1 line)
                // @checkstyle IllegalCatchCheck (1 line)
            } catch (final Exception exe) {
                throw new SQLException(exe);
            }
        } catch (final DatabaseException dexe) {
            throw new SQLException(dexe);
        }
    }
}
