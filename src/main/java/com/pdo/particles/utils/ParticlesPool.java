/**************************************************************************
 Particles Explorer
 Copyright (C) 2000-2002  Poil d'Ortie

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 **************************************************************************/

package com.pdo.particles.utils;

/**
 * A pool of particles
 *
 * @author Poil d'Ortie
 */
public class ParticlesPool {
    private Particle[] pool;

    private int size;

    /**
     * Default Constructor
     *
     * @param size the pool size
     */
    public ParticlesPool(int size) {
        this.size = size;
        pool = new Particle[size];
        for (int i = 0; i < size; i++) {
            pool[i] = new Particle();
        }
    }

    /**
     * Returns the first available particle.
     *
     * @return Particle
     */
    public Particle getFirstFreeParticle() {
        int i = 0;
        while (i < size) {
            Particle part = pool[i];
            if (part.getAge() < 1) {
                return part;
            }
            i++;
        }
        return null;
    }

    public int getPoolSize() {
        return pool.length;
    }

    public Particle getParticleAt(int i) {
        return pool[i];
    }
}