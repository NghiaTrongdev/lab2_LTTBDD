package com.example.lab2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.databinding.ItemContainerBinding;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>{
    private ArrayList<Contact> listContact;

    public interface OnItemCheckedListener {
        void onItemChecked(boolean isChecked);
    }
    private OnItemCheckedListener onItemCheckedListener;

    public void setOnItemCheckedListener(OnItemCheckedListener listener) {
        this.onItemCheckedListener = listener;
    }



    public ContactAdapter(ArrayList<Contact> listContact ) {
        this.listContact = listContact;
    }


    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       ItemContainerBinding itemContainerBinding = ItemContainerBinding.inflate(
               LayoutInflater.from(parent.getContext()),
               parent,
               false
       );
       return new ContactHolder(itemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {

        holder.setData(listContact.get(position));
    }

    @Override
    public int getItemCount() {
        return listContact.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private ItemContainerBinding binding;
         public ContactHolder(ItemContainerBinding itemContainerBinding){
             super(itemContainerBinding.getRoot());
            this.binding = itemContainerBinding;

        }
        private void setData(Contact contact){
                 binding.textViewName.setText(contact.getName());
                 binding.textViewPhone.setText(contact.getPhoneNumber());
        }
        private void setListener(){
             binding.checkbox.setOnCheckedChangeListener((buttonView,isChecked) -> {
                 if(onItemCheckedListener !=null){
                    onItemCheckedListener.onItemChecked(isChecked);
                    notifyDataSetChanged();
                 }
             });
        }
    }

}
