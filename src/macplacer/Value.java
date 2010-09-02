/*
 *************************************************************************
 *************************************************************************
 **                                                                     **
 **  MACPLACER                                                          **
 **  Copyright (C) 2010         Karl W. Pfalzer                         **
 **                                                                     **
 **  This program is free software; you can redistribute it and/or      **
 **  modify it under the terms of the GNU General Public License        **
 **  as published by the Free Software Foundation; either version 2     **
 **  of the License, or (at your option) any later version.             **
 **                                                                     **
 **  This program is distributed in the hope that it will be useful,    **
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of     **
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the      **
 **  GNU General Public License for more details.                       **
 **                                                                     **
 **  You should have received a copy of the GNU General Public License  **
 **  along with this program; if not, write to the                      **
 **  Free Software Foundation, Inc.                                     **
 **  51 Franklin Street, Fifth Floor                                    **
 **  Boston, MA  02110-1301, USA.                                       **
 **                                                                     **
 *************************************************************************
 *************************************************************************
 */
package macplacer;

/**
 * Wrap scalar object (typically Integer, Double, ...) such that any
 * value updates are reflected in that object.
 * For example, in the following:
 *     Integer i1 = 0, i2 = i1;
 *     i1++;
 * After "i1++", the value of i1 is 1 and i2 is 0.
 * In order to keep an object reference for such operations, use
 * a subclass of Value such as IntegerValue, DoubleValue.
 * @author karl
 * @param <T> type of scalar object.
 */
public class Value<T> {
	public Value(T v) {
		val = v;
	}
	T val;
};
