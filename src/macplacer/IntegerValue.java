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
 * Wrap Integer to allow value to be maintained in all references.
 * For example:
 *     (1) IntegerValue i1 = new IntegerValue(5), i2 = i1;
 *     (2) Integer i3 = 5, i4 = i3;
 *     (3) i1.val++; i3++;
 * After (3), i3==6, i4==5, i1.val==i2.val==6.
 * @author karl
 */
public class IntegerValue extends Value<Integer> {
	public IntegerValue(Integer v) {
		super(v);
	}
	public IntegerValue() {
		this(0);
	}
};
