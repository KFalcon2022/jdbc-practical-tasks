package com.walking.jdbc.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TicketMapperTest {
    private final TicketMapper ticketMapper = new TicketMapper();

    @Test
    void map_value_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);
        doReturn(true).when(rs).next();
        doReturn(new Timestamp(0)).when(rs).getTimestamp(any());

//        when
        var actual = ticketMapper.map(rs);

//        then
        assertTrue(actual.isPresent());
        verify(rs).next();

        verify(rs, times(2)).getString(any());
        verify(rs, times(2)).getLong(any());
        verify(rs, times(3)).getTimestamp(any());
    }

    @Test
    void map_empty_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);

//        when
        var actual = ticketMapper.map(rs);

//        then
        assertEquals(Optional.empty(), actual);
        verify(rs).next();

        verify(rs, never()).getString(any());
        verify(rs, never()).getLong(any());
        verify(rs, never()).getTimestamp(any());
    }

    @Test
    void map_SQLException_failed() throws SQLException {
        var rs = mock(ResultSet.class);
        doThrow(SQLException.class).when(rs).next();

        Executable executable = () -> ticketMapper.map(rs);

        assertThrows(SQLException.class, executable);
        verify(rs).next();

        verify(rs, never()).getString(any());
        verify(rs, never()).getLong(any());
        verify(rs, never()).getTimestamp(any());
    }

    @Test
    void mapMany_empty_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);

//        when
        var actual = ticketMapper.mapMany(rs);

//        then
        assertTrue(actual.isEmpty());
        verify(rs).next();

        verify(rs, never()).getString(any());
        verify(rs, never()).getLong(any());
        verify(rs, never()).getTimestamp(any());
    }

    @Test
    void mapMany_value_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn(new Timestamp(0)).when(rs).getTimestamp(any());

//        when
        var actual = ticketMapper.mapMany(rs);

//        then
        assertEquals(1, actual.size());
        verify(rs, times(2)).next();

        verify(rs, times(2)).getString(any());
        verify(rs, times(2)).getLong(any());
        verify(rs, times(3)).getTimestamp(any());
    }

    @Test
    void mapMany_multipleValues_success() throws SQLException {
//        given
        var rs = mock(ResultSet.class);
        doReturn(true)
                .doReturn(true)
                .doReturn(false)
                .when(rs).next();
        doReturn(new Timestamp(0)).when(rs).getTimestamp(any());

//        when
        var actual = ticketMapper.mapMany(rs);

//        then
        assertEquals(2, actual.size());
        verify(rs, times(3)).next();

        verify(rs, times(4)).getString(any());
        verify(rs, times(4)).getLong(any());
        verify(rs, times(6)).getTimestamp(any());
    }

    @Test
    void mapMany_SQLException_failed() throws SQLException {
        var rs = mock(ResultSet.class);
        doThrow(SQLException.class).when(rs).next();

        Executable executable = () -> ticketMapper.mapMany(rs);

        assertThrows(SQLException.class, executable);
        verify(rs).next();

        verify(rs, never()).getString(any());
        verify(rs, never()).getLong(any());
        verify(rs, never()).getTimestamp(any());
    }
}