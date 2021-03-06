import {User} from './user';
/**
 * Created by rjaf on 24/06/2017.
 */
export class Class {

  private _id: number;
  private _name: string;
  private _abbreviation: string;
  private _user: User;

  constructor( name: string, abbreviation: string) {
    this._name = name;
    this._abbreviation = abbreviation;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get abbreviation(): string {
    return this._abbreviation;
  }

  get user(): User {
    return this._user;
  }

  set user(value: User) {
    this._user = value;
  }

  set abbreviation(value: string) {
    this._abbreviation = value;
  }
}
